package src.main.scala

object highLevel_function_train extends App{
  //插值和多行字符串
  val s = "Hello"
  val name = "PK"

  println(s"Hello:$name")
  val team = "Ma De Li"
  println(s"Hello:$name, Welcome to $team")
  //证明必须加s才行
  println("Hello:$name, Welcome to $team")

  val ss =
    """
      |123
      |hello
      |scala
    """.stripMargin
  println(ss)
  //currying函数，将原来接受两个参数的一个函数，转换成2个
  def sum(a:Int, b:Int) = a+b
  //println(sum(2, 3))

  def sum2(a:Int)(b:Int) = a + b
  //println(sum2(2)(3))

  //高阶函数
  val list = List(1, 2, 3, 4, 5, 6, 8)
  //map：逐个去操作集合中的每个元素
  val l1 = list.map((x:Int) => x + 1)
  //println(l1)
  val l2 = list.map((x) => x * 2)
  //println(l2)
  //println(list.map(_ * 3))
  //list.map(_ * 3).foreach(println)
  //filter函数
  //list.map(_ * 2).filter(_ > 8).foreach(println)

  val txt = scala.io.Source.fromFile("D:/hellospark.txt").mkString
  println(txt)
  val txts = List(txt)
  txts.flatMap(_.split(" |\n").map(x => (x, 1))).foreach(println)
  println("~~~~~~~~~~")
  txts.flatMap(_.split(" |\n").map(x => (x, 1))).groupBy(_._1).mapValues(_.size).foreach(println)

  //偏函数
  // A 输入参数类型 B 输出参数类型
  def sayChinese:PartialFunction[String, String] ={
    case "A" => "a..."
    case "B" => "b..."
    case _ => "....."
  }
  println(sayChinese("A"))
  //println(sayChinese(2))
}
