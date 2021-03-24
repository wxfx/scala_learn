package src.main.scala

import java.io.File

object implicit_conversion_train extends App{
  //定义隐式转换函数即可
  implicit def man2superman(man:Man):Superman = new Superman(man.name)
  val man = new Man("PK")
  //man.fly()
  //man.eat()

  //文件过来直接读
  implicit def file2RichFile(file: File):RichFile = new RichFile(file)
  val file = new File("D:/hellospark.txt")
  val txt = file.read()
  //println(txt)

  //隐式参数
  implicit val test = "test"
  def testParsm(implicit name:String): Unit ={
    println(name + "~~~~~~~~~~~~~~~")
  }

  //implicit val s1 = "kkkkk"
  testParsm("keyan")
  testParsm

  //隐式类
  implicit class Calculator(x:Int){
    def add(a:Int) = a + x
  }
  println(12.add(3))
}

class Man(val name:String){
  def eat(): Unit ={
    println(s"man [ $name ] eat ....")
  }
}


class Superman(val name:String){
  def fly(): Unit ={
    println(s"superman [ $name ] fly ....")
  }
}

class RichFile(val file:File){
  def read() = {
    scala.io.Source.fromFile(file.getPath).mkString
  }
}
