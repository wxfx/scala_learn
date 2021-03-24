package app

import java.text.SimpleDateFormat
import java.util.Date

import bean.DauInfo
import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.Jedis
import util.{MyEsUtil, MyKafkaUtil, OffsetManager, RedisUtil}

import scala.collection.mutable.ListBuffer

object DauApp {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("DauApp").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val topic = "GMALL_START"
    val groupId = "GMALL_DAU_CONSUMER"
    //从redis中读取当前最新偏移量, 启动执行一次
    val startOffset = OffsetManager.getOffset(groupId, topic)
    var startInputDstream: InputDStream[ConsumerRecord[String, String]] = null
    // 判断如果从redis中读取当前最新偏移量，则用该偏移量加载到kafka中的数据，否则直接用kafka读出默认最新的数据
    if (startOffset != null && startOffset.size>0) {
      startInputDstream = MyKafkaUtil.getKafkaStream(topic, ssc, startOffset, groupId)
    }else{
      startInputDstream =MyKafkaUtil.getKafkaStream(topic, ssc,  groupId)
    }

    //获得本批次偏移量的移动后的新位置
    var startupOffsetRanges:Array[OffsetRange] = null
    val startupInputGetOffsetDstreamstar: DStream[ConsumerRecord[String, String]] = startInputDstream.transform { rdd =>
      startupOffsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }

    //第一步：测试数据是否进来
    //startupInputDstream.map(_.value).print(100)
    //第二步实现具体逻辑
    val startLogInfoDStream = startupInputGetOffsetDstreamstar.map{record=>
      val startupJson = record.value()
      val startupJSONObj = JSON.parseObject(startupJson)
      startupJSONObj
    }
    //加入去重清单 日活 每天一个清单key:每天一个key
    //startLogInfoDStream.map{jsonObj=>
    // //Redis 写入type? set key? dau:2020-11-15 value? mid
    //  val dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date(jsonObj.getLong("ts")))
    //  //key
    //  val dauKey = "dau:" + dateStr
    //  println("dauKey:" + dauKey)
    //  val jedis = new Jedis("master", 6379)
    //  //value
    //  val mid = jsonObj.getJSONObject("common").getString("mid")
    //  jedis.sadd(dauKey, mid)
    //  jedis.close()
    //}
    val startJsonObjWithDauDstream = startLogInfoDStream.mapPartitions{ jsonObjItr =>
      val jedis = RedisUtil.getJedisClient
      val jsonObjList = jsonObjItr.toList
      println("过滤前：" + jsonObjList.size)
      val jsonObjFilteredList = new ListBuffer[JSONObject]()
      for (jsonObj <- jsonObjList) {
        val dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date(jsonObj.getLong("ts")))
        val dauKey = "dau:" + dateStr
        ///println("dauKey:" + dauKey)
        val mid = jsonObj.getJSONObject("common").getString("mid")
        //是否第一次插入,为1证明redis里面还没有
        val isFirstFlag = jedis.sadd(dauKey, mid)
        if (isFirstFlag == 1L){
          jsonObjFilteredList += jsonObj
        }
        //一天后过期
        jedis.expire(dauKey, 3600*24*7)
      }
      jedis.close()
      println("过滤后: " + jsonObjFilteredList.size)
      jsonObjList.toIterator
    }
    //println("来到输出之前")
    //startJsonObjWithDauDstream.print(1000)

    //第三步:将数据变换结构
    val dauInfoDstream = startJsonObjWithDauDstream.map { jsonObj =>
      val commonJsonObj = jsonObj.getJSONObject("common")
      val dateTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(jsonObj.getLong("ts")))
      val dateTimeArr = dateTimeStr.split(" ")
      //日期
      val dt = dateTimeArr(0)
      //小时：分钟
      val timeArr = dateTimeArr(1).split(":")
      //小时
      val hr = timeArr(0)
      //分钟
      val mi = timeArr(1)
      DauInfo(commonJsonObj.getString("mid"),
        commonJsonObj.getString("uid"),
        commonJsonObj.getString("ar"),
        commonJsonObj.getString("ch"),
        commonJsonObj.getString("vc"),
        dt,
        hr,
        mi,
        jsonObj.getLong("ts")
      )
    }
    //dauInfoDstream.print(100)

    //第四步：将数据插入gmall_dau_info_2020****索引中
    dauInfoDstream.foreachRDD{rdd=>
      val infoes = rdd.collect()
      infoes.splitAt(8)
      rdd.foreachPartition{dauInfoItr=>
       //观察偏移量移动
        val offsetRange = startupOffsetRanges(TaskContext.getPartitionId())
        println("偏移量:" + " from " + offsetRange.fromOffset + "->" + offsetRange.untilOffset)
        //写入es
        val dataList:List[(String, DauInfo)] = dauInfoItr.toList.map { dauInfo =>
          (dauInfo.mid, dauInfo)
        }
        val dt = new SimpleDateFormat("yyyyMMdd").format(new Date())
        val indexName = "gmall_dau_info_" + dt
        MyEsUtil.saveBulk(dataList, indexName)
      }
      //偏移量的提交
      OffsetManager.saveOffset(groupId, topic, startupOffsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()
  }



}
