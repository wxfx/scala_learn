package util

import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.OffsetRange

object OffsetManager {
  //把redis中的偏移量读取出来，并转换成Kafka需要偏移量格式
  def getOffset(groupId:String, topic:String)={
    val jedis = RedisUtil.getJedisClient
    //Redis type? hash key? "offset:[groupid]:[topic]" field? partition value? offset
    val offsetKey = "offset:" + groupId + ":" + topic
    //通过一个key查询hash的所有值
    val redisOffsetMap = jedis.hgetAll(offsetKey)
    jedis.close()
    import scala.collection.JavaConversions._
    val kafkaOffsetMap = redisOffsetMap.map{case (partitionId, offsetStr)=>(new TopicPartition(topic, partitionId.toInt), offsetStr.toLong)}
    kafkaOffsetMap.toMap
  }

  def saveOffset(groupId:String, topic:String, offsetRanges: Array[OffsetRange])={
    if (offsetRanges != null){
      val jedis = RedisUtil.getJedisClient
      val offsetKey = "offset:" + groupId + ":" + topic
      val offsetMap = new java.util.HashMap[String, String]()
      //把每个分区的新的偏移量提取并组合
      var needSaveFlag = false
      for (offsetRange <- offsetRanges){
        if (offsetRange.fromOffset < offsetRange.untilOffset){
          needSaveFlag = true
        }
        println("分区：" + offsetRange.partition + " from " + offsetRange.fromOffset + "->" + offsetRange.untilOffset)
        offsetMap.put(offsetRange.partition.toString, offsetRange.untilOffset.toString)
      }
      //把各个分区的新偏移量写入redis
      if(needSaveFlag){
        jedis.hmset(offsetKey, offsetMap)
      }
      jedis.close()
    }
  }

}
