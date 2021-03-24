package app.dim

import bean.dim.{BaseCategory3, BaseTrademark, SpuInfo}
import com.alibaba.fastjson.JSON
import org.apache.hadoop.conf.Configuration
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.{MyKafkaUtil, OffsetManager}

object SpuInfoApp{
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("dim_base_category3_app")

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val topic = "ODS_T_SPU_INFO"
    val groupId = "dim_spu_info_group"

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

    val objectDstream: DStream[SpuInfo] = inputGetOffsetDstream.map { record =>
      val jsonStr: String = record.value()
      val obj: BaseCategory3 = JSON.parseObject(jsonStr, classOf[SpuInfo])
      obj
    }

    objectDstream.foreachRDD{rdd=>
      import org.apache.phoenix.spark._
      rdd.saveToPhoenix("GMALL1122_SPU_INFO", Seq("ID", "SPU_NAME"),
        new Configuration, Some("master,slave1,slave2:2181"))
      OffsetManager.saveOffset(groupId, topic, offsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
