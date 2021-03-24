package app.ods

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.{MyKafkaSink, MyKafkaUtil, OffsetManager}

object BaseDBMaxwellApp {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("ods_base_db_canal_app")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    // C代表canal
    val topic = "ODS_DB_GMALL2020_M"
    val groupId = "base_db_maxwell_group"
    val offset = OffsetManager.getOffset(groupId, topic)

    var inputDstream: InputDStream[ConsumerRecord[String, String]] = null
    // 判断如果从redis中读取当前最新偏移量 则用该偏移量加载kafka中的数据  否则直接用kafka读出默认最新的数据
    if(offset!=null&&offset.size>0){
      inputDstream = MyKafkaUtil.getKafkaStream(topic,ssc,offset,groupId)
      //startInputDstream.map(_.value).print(1000)
    }else{
      inputDstream  = MyKafkaUtil.getKafkaStream(topic,ssc,groupId)
    }

    //取得偏移量步长
    var offsetRanges:Array[OffsetRange] = null
    val inputGetOffsetDstream = inputDstream.transform { rdd =>
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }

    val dbJsonObjDstream = inputGetOffsetDstream.map { record =>
      val jsonString = record.value()
      val jsonObj: JSONObject = JSON.parseObject(jsonString)
      jsonObj
    }
//{"database":"gmall2020","table":"z_user_info","type":"insert","ts":1605617094,"xid":1524,"commit":true,"data":{"id":31,"user_name":"li4","tel":"1389999999"}}
    dbJsonObjDstream.foreachRDD{rdd=>
      rdd.foreachPartition{jsonObjItr=>
        for(jsonObj <- jsonObjItr){
          val dataJsonObj:JSONObject = jsonObj.getJSONObject("data")
          println("dataJsonObj:" + dataJsonObj)
          val topic = "ODS_T_" + jsonObj.getString("table").toUpperCase()
          val id = dataJsonObj.getString("id")
          println("topic:" + topic + ",id:" + id)
          MyKafkaSink.send(topic, id, dataJsonObj.toJSONString)
        }
      }
      //如果中间挂了，这里可以允许kafka有冗余数据
      OffsetManager.saveOffset(groupId, topic,  offsetRanges)
      }
    ssc.start()
    ssc.awaitTermination()


  }

}
