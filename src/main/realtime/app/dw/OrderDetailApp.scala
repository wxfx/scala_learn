package app.dw

import java.lang

import bean.OrderDetail
import bean.dim.BaseCategory3
import com.alibaba.fastjson.serializer.SerializeConfig
import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.hadoop.conf.Configuration
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.{MyKafkaSink, MyKafkaUtil, OffsetManager, PhoenixUtil}

object OrderDetailApp{
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("dim_base_category3_app")

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val topic = "ODS_T_ORDER_DETAIL"
    val groupId = "dw_order_detail_group"

    //////////////////////偏移量管理//////////////////////
    val offset: Map[TopicPartition, Long] = OffsetManager.getOffset(groupId, topic)

    var inputDStream: InputDStream[ConsumerRecord[String, String]] = null
    //判断如果从redis中读取当前最新偏移量，则用该偏移量加载kafka中的数据，否则直接用kafka读出默认的最新的数据
    if (offset != null && offset.size > 0){
      inputDStream = MyKafkaUtil.getKafkaStream(topic, ssc, offset, groupId)
    } else{
      inputDStream = MyKafkaUtil.getKafkaStream(topic, ssc, groupId)
    }

    //取得偏移量步长
    var offsetRanges:Array[OffsetRange] = null
    val inputGetOffsetDstream: DStream[ConsumerRecord[String, String]] = inputDStream.transform { rdd =>
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }

    //业务办理
    val orderDetailDstream: DStream[OrderDetail] = inputGetOffsetDstream.map { record =>
      val jsonStr: String = record.value()
      val orderDetail: OrderDetail = JSON.parseObject(jsonStr, classOf[OrderDetail])
      orderDetail
    }

    val orderDetailWithSkuDstream: DStream[OrderDetail] = orderDetailDstream.mapPartitions { orderDetailItr =>
      val orderDetailList: List[OrderDetail] = orderDetailItr.toList
      if (orderDetailList.size > 0) {
        val skuIdList: List[Long] = orderDetailList.map(_.sku_id)
        val sql: String = "selecxt id, tm_id, spu_id, category3_id, tm_name, spu_name, category3_name from gmall1122_sku_info where id in ('" +
          skuIdList.mkString("','") + "')"
        val skuJsonObjList: List[JSONObject] = PhoenixUtil.queryList(sql)
        val skuJsonObjMap: Map[Long, JSONObject] = skuJsonObjList.map(skuJsonObj => (skuJsonObj.getLongValue("ID"), skuJsonObj)).toMap
        for (orderDetail <- orderDetailList) {
          val skuJsonObj: JSONObject = skuJsonObjMap.getOrElse(orderDetail.sku_id, null)
          orderDetail.spu_id = skuJsonObj.getLong("SPU_ID")
          orderDetail.spu_name = skuJsonObj.getString("SPU_NAME")
          orderDetail.tm_id = skuJsonObj.getLong("TM_ID")
          orderDetail.tm_name = skuJsonObj.getString("TM_NAME")
          orderDetail.category3_id = skuJsonObj.getLong("CATEGORY3_ID")
          orderDetail.category3_name = skuJsonObj.getString("CATEGORY3_NAME")
        }
      }
      orderDetailList.toIterator
    }

    orderDetailWithSkuDstream.cache()
    orderDetailWithSkuDstream.print(1000)

    //写入es
    //println("订单数：" + .count())
    orderDetailWithSkuDstream.foreachRDD{rdd=>
      rdd.foreachPartition{orderDetailItr=>
        val orderDetailList: List[OrderDetail] = orderDetailItr.toList
        for (orderDetail <- orderDetailList){
          MyKafkaSink.send("DW_ORDER_DETAIL", orderDetail.order_id.toString, JSON.toJSONString(orderDetail, new SerializeConfig(true)))
        }
      }
      OffsetManager.saveOffset(groupId, topic, offsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
