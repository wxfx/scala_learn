package app.ads

import java.text.SimpleDateFormat
import java.util.Date

import bean.OrderDetailWide
import com.alibaba.fastjson.JSON
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scalikejdbc.config.DBs
import scalikejdbc.{DB, SQL}
import util.{MyKafkaUtil, OffsetManager, OffsetManagerMysql}


object TrademarkAmountSumApp{
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("dim_base_category3_app")

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val topic = "DWS_ORDER_DETAIL_WIDE"
    val groupId = "ads_trademark_sum_group"

    //////////////////////偏移量管理//////////////////////
    //改成mysql
    val offset: Map[TopicPartition, Long] = OffsetManagerMysql.getOffset(groupId, topic)

    var inputDStream: InputDStream[ConsumerRecord[String, String]] = null
    //判断如果从redis中读取当前最新偏移量，则用该偏移量加载kafka中的数据，否则直接用kafka读出默认的最新的数据
    if (offset != null && offset.size > 0) {
      inputDStream = MyKafkaUtil.getKafkaStream(topic, ssc, offset, groupId)
    } else {
      inputDStream = MyKafkaUtil.getKafkaStream(topic, ssc, groupId)
    }

    //取得偏移量步长
    var offsetRanges: Array[OffsetRange] = null
    val inputGetOffsetDstream: DStream[ConsumerRecord[String, String]] = inputDStream.transform { rdd =>
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }

    val orderDetailWideDstream: DStream[OrderDetailWide] = inputGetOffsetDstream.map { record =>
      val jsonStr: String = record.value()
      val orderDetailWide: OrderDetailWide = JSON.parseObject(jsonStr, classOf[OrderDetailWide])
      orderDetailWide
    }
    val orderWideWithKeyDstream: DStream[(String, Double)] = orderDetailWideDstream.map(orderDetailWide => (orderDetailWide.tm_id+ ":" + orderDetailWide.tm_name, orderDetailWide.final_detail_amount))

    val orderWideSumDstream: DStream[(String, Double)] = orderWideWithKeyDstream.reduceByKey { case ( amount1, amount2) =>
      java.lang.Math.round((amount1 + amount2)/100) * 100
    }
    //保存数据字段：时间（业务时间也行），维度，度量 stat_time ,tm_id, tm_name, amount, (sku_num)(order_count)
    //保存偏移量
    orderWideSumDstream.foreachRDD{rdd=>
      //把各个executor中各个分区的数据收集到driver中的一个数组
      val orderWideArr: Array[(String, Double)] = rdd.collect()
      //scalikejdbc
      if (orderWideArr != null && orderWideArr.size > 0 ){
        DBs.setup()
        DB.localTx(implicit session=>{ //事务启动
          //偏移量保存完毕
          for (offset <- offsetRanges){
            println(offset.partition + "::" + offset.untilOffset)
            SQL("replace into `offset_1122(group_id, topic, partition_id, topic_offset) values (?,?,?,?)").bind(groupId, topic, offset.partition,offset.untilOffset).update().apply()
          }
          throw new RuntimeException("强行测试异常!!")
          for ((tm, amount) <- orderWideArr){
            val statTime: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date)
            val tmArr: Array[String] = tm.split(":")
            val tm_id = tmArr(0)
            val tm_name = tmArr(1)
            SQL("INSERT INTO trademark_amount_sum_stat VALUES(?,?,?,?)").bind(statTime, tm_id
            ,tm_name, amount).update().apply()
          }//事务结束
        })
      }
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
