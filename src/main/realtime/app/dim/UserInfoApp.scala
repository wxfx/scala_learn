package app.dim

import java.text.SimpleDateFormat
import java.util.Date

import bean.dim.{BaseCategory3, BaseTrademark, UserInfo}
import com.alibaba.fastjson.JSON
import org.apache.hadoop.conf.Configuration
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.{MyKafkaUtil, OffsetManager}

object UserInfoApp{
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("dim_base_category3_app")

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val topic = "ODS_T_BASE_TRADEMARK"
    val groupId = "dim_base_trademark_group"

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

    val userInfoDstream: DStream[UserInfo] = inputGetOffsetDstream.map { record =>
      val userInfoJsonStr: String = record.value()
      val userInfo: UserInfo = JSON.parseObject(userInfoJsonStr, classOf[UserInfo])
      val formattor = new SimpleDateFormat("yyyy-MM-dd")
      val date: Date = formattor.parse(userInfo.birthday)
      val curTs: Long = System.currentTimeMillis()
      val betweenMs: Long = curTs - date.getTime //两个ts
      val age: Long = betweenMs / 1000L / 60L / 60L / 24L / 365L
      if (age < 20) {
        userInfo.age_group = "20岁及以下"
      } else if (age > 30) {
        userInfo.age_group = "30岁以上"
      } else {
        userInfo.age_group = "21岁到30岁"
      }
      if (userInfo.gender == "M") {
        userInfo.gender_name = "男"
      } else {
        userInfo.gender_name = "女"
      }
      userInfo
    }

    userInfoDstream.foreachRDD{rdd=>
      import org.apache.phoenix.spark._
      rdd.saveToPhoenix("GMALL1122_USER_INFO", Seq("ID", "USER_LEVEL", "BIRTHDAY", "GENDER", "AGE_GROUP", "GENDER_NAME"),
        new Configuration, Some("master,slave1,slave2:2181"))
      OffsetManager.saveOffset(groupId, topic, offsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
