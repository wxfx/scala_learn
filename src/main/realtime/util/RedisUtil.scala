package util

import redis.clients.jedis.{JedisPool, JedisPoolConfig}

object RedisUtil {
  var jedisPool:JedisPool = null
  def getJedisClient = {
    //println("redis客户端进来了")
    if(jedisPool == null){
      //println("开辟一个连接池")
      val config = PropertiesUtil.load("config.properties")
      val host = config.getProperty("redis.host")
      val port = config.getProperty("redis.port")
      //println("host:" +host)
      //println("port:" +port)

      val jedisPoolConfig = new JedisPoolConfig()
      //最大连接数
      jedisPoolConfig.setMaxTotal(100)
      //最大空闲
      jedisPoolConfig.setMaxIdle(20)
      //最小空闲
      jedisPoolConfig.setMinIdle(1)
      //忙碌时是否等待
      jedisPoolConfig.setBlockWhenExhausted(true)
      //忙碌时等待时长，单位：毫秒
      jedisPoolConfig.setMaxWaitMillis(500)
      //每次获得连接的进行测试
      jedisPoolConfig.setTestOnBorrow(true)

      jedisPool = new JedisPool(jedisPoolConfig, host, port.toInt)
    }
    //println(s"jedisPool.getNumActive = ${jedisPool.getNumActive}")
    //println("getResouce:" + jedisPool.getResource)
    //println("获得一个连接")
    jedisPool.getResource
  }

}
