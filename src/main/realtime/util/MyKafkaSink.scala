package util

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object MyKafkaSink {
  private val properties: Properties = PropertiesUtil.load("config.properties")
  private val broker_list: String = properties.getProperty("kafka.broker.list")
  var kafkaProducer: KafkaProducer[String, String] = null

  def createKafkaProducer:KafkaProducer[String, String] = {
    val properties = new Properties
    properties.put("bootstrap.servers", broker_list)
    properties.put("max.poll.records", "1000000")
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("enable.idompotence", (true:java.lang.Boolean))
    var producer:KafkaProducer[String, String] = null
    try{
      producer = new KafkaProducer[String, String](properties)
    }
    catch {
      case e:Exception=>
        e.printStackTrace()
    }
    producer
  }

  def send(topic:String, msg:String)={
    if (kafkaProducer == null)
      kafkaProducer = createKafkaProducer
    kafkaProducer.send(new ProducerRecord[String, String](topic, msg))
  }

  def send(topic:String, key:String, msg:String)={
    if (kafkaProducer == null)
      kafkaProducer = createKafkaProducer
    println("kafkaProducer不为空!")
    kafkaProducer.send(new ProducerRecord[String, String](topic, key, msg))
  }


}
