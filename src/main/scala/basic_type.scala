package src.main.scala

import scala.io.Source._

object basic_type {
  def main(args: Array[String]): Unit = {
    //注意编码, GB2312
    //lazy val info = fromFile("D:/gftg_svn/设计文档/操作文档/广发托管git操作.txt").mkString
    //println(info)
    def add(x:Int, y:Int):Int = {
      x + y
    }
    //println(add(8, 9))

    //ef three() = 1 + 2
    //rint(three)
    def three(x:Int=1, y:Int=2) = x + y
    //println(three())
    //println(three(10000, 20000))
    //println(three(y=20, x=10))
    def sum(numbers:Int*) = {
      var result = 0
      for (number <- numbers){
        result += number
      }
      result
    }
    println(sum(1, 9, 8, 7, 0, 1))
    val x = 1
    val adj = if (x > 0) true else false
    //println(adj)

    for(i <- 1 to 10 if i % 2 == 0){
      //println(i)
    }
    val courses = Array("Hive", "Scala", "Spark", "Hive")
    //courses.foreach(c => println(c))
    var (num, sum1) = (100, 0)
    while (num > 0){
      sum1 = sum1 + num
      num = num - 1
    }
    //println(sum1)



  }
}
