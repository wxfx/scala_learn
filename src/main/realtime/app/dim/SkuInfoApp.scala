package app.dim

import bean.dim.{BaseCategory3, BaseTrademark, SkuInfo}
import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.hadoop.conf.Configuration
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.{MyKafkaUtil, OffsetManager, PhoenixUtil}

object SkuInfoApp{
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("dim_base_category3_app")

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val topic = "ODS_T_SKU_INFO"
    val groupId = "dim_sku_info_group"

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

    val objectDstream: DStream[SkuInfo] = inputGetOffsetDstream.map { record =>
      val jsonStr: String = record.value()
      val obj: BaseCategory3 = JSON.parseObject(jsonStr, classOf[SkuInfo])
      obj
    }

    val skuInfoDstream: DStream[SkuInfo] = objectDstream.transform { rdd =>
      if (rdd.count() > 0) {
        //category3
        val category3Sql = "select if, name from gmall1122_base_category3" //周期性执行
        val category3List: List[JSONObject] = PhoenixUtil.queryList(category3Sql)
        val category3Map: Map[String, JSONObject] = category3List.map(jsonObj => (jsonObj.getString("ID"), jsonObj)).toMap

        //tm_name
        val tmSql = "select id, tm_name from gmall1122_base_trademark"
        val tmList: List[JSONObject] = PhoenixUtil.queryList(tmSql)
        val tmMap: Map[String, JSONObject] = tmList.map(jsonObj => (jsonObj.getString("ID"), jsonObj)).toMap

        // qpu
        val spuSql = "select id, spu_name from gmall1122_spu_info"
        val spuList: List[JSONObject] = PhoenixUtil.queryList(spuSql)
        val spuMap: Map[String, JSONObject] = spuList.map(jsonObj => (jsonObj.getString("ID"), jsonObj)).toMap

        //汇总到一个list， 广播这个map
        val dimList: List[Map[String, JSONObject]] = List[Map[String, JSONObject]](category3Map, tmMap, spuMap)
        val dimBC: Broadcast[List[Map[String, JSONObject]]] = ssc.sparkContext.broadcast(dimList)

        val skuInfoRDD: RDD[SkuInfo] = rdd.mapPartitions { skuInfoItr =>
          //extra
          val dimList: List[Map[String, JSONObject]] = dimBC.value //接收broadcast的变量
          val category3Map: Map[String, JSONObject] = dimList(0)
          val tmMap: Map[String, JSONObject] = dimList(1)
          val spuMap: Map[String, JSONObject] = dimList(2)

          val skuInfoList: List[SkuInfo] = skuInfoItr.toList
          for (skuInfo <- skuInfoList) {
            val category3JsonObj: JSONObject = category3Map.getOrElse(skuInfo.category3_id, null) //从map中寻值
            if (category3JsonObj != null) {
              skuInfo.category3_name = category3JsonObj.getString("NAME")
            }

            val tmJsonObj: JSONObject = tmMap.getOrElse(skuInfo.tm_id, null) //从map中寻值
            if (tmJsonObj != null) {
              skuInfo.tm_name = tmJsonObj.getString("TM_NAME")
            }
            val spuJsonObj: JSONObject = spuMap.getOrElse(skuInfo.spu_id, null)
            if (spuJsonObj != null) {
              skuInfo.spu_name = spuJsonObj.getString("SPU_NAME")
            }
          }
          skuInfoList.toIterator
        }
        skuInfoRDD
      } else {
        rdd
      }
    }

    skuInfoDstream.foreachRDD{rdd=>
      import org.apache.phoenix.spark._
      rdd.saveToPhoenix("GMALL1122_SKU_INFO", Seq("ID", "SPU_ID", "PRICE", "SKU_NAME", "TM_ID", "CATEGORY3_ID", "CREATE_TIME", "CATEGORY3_NAME", "SPU_NAME", "TM_NAME")
        , new Configuration, Some("master,slave1,slave2:2181"))
      OffsetManager.saveOffset(groupId, topic, offsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
