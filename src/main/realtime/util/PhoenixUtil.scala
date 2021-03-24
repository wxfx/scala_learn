package util

import java.sql._

import com.alibaba.fastjson.JSONObject
import util.MysqlUtil.queryList

import scala.collection.mutable.ListBuffer


object PhoenixUtil {
  def main(args: Array[String]): Unit = {
    val list: List[JSONObject] = queryList("select * from customer0919")
    println(list)

  }

  def queryList(sql:String): List[JSONObject] = {
    Class.forName("com.apache.phoenix.jdbc.PhoenixDriver")
    val resultList: ListBuffer[JSONObject] = new ListBuffer[JSONObject]()
    val conn: Connection = DriverManager.getConnection("jdbc:phoenix:master,slave1,slave2:2181")
    val stat: Statement = conn.createStatement
    println(sql)
    val rs: ResultSet = stat.executeQuery(sql)
    val md: ResultSetMetaData = rs.getMetaData
    while(rs.next()){
      val rowData = new JSONObject()
      for (i <- 1 to md.getColumnCount){
        rowData.put(md.getColumnName(i), rs.getObject(i))
      }
      resultList += rowData
    }
    stat.close()
    conn.close()
    resultList.toList
  }

}
