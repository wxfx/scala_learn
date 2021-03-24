package util

import org.apache.kafka.common.TopicPartition
import java.util

import com.alibaba.fastjson.JSONObject
object OffsetManagerMysql {

  /**
   * //把mysql中的偏移量读取出来，并转换成Kafka需要偏移量格式
   * @param groupId
   * @param topic
   * @return
   */
  def getOffset(groupId:String, topic:String)={
    var offsetMap = Map[TopicPartition, Long]()

    val offsetJsonObjList: List[JSONObject]= MysqlUtil.queryList("select group_id, topic, partition_id, topic_offset " +
      "where group_id='"+ groupId +"' and topic='" + topic + "'")

    if (offsetJsonObjList != null && offsetJsonObjList.size == 0){
      null
    }else{
      val kafkaOffsetList: List[(TopicPartition, Long)] = offsetJsonObjList.map{offsetJsonObj=>(new TopicPartition(offsetJsonObj.getString("topic"), offsetJsonObj.getIntValue("partition_id")), offsetJsonObj.getLongValue("topic_offset"))}
      kafkaOffsetList.toMap
    }
  }
}
