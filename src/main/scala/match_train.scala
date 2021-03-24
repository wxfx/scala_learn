package src.main.scala

import scala.util.Random

object match_train extends App{
  val names = Array("A", "B", "C")
  val name = names(Random.nextInt(names.length))
  name match {
    case "A" => println("A...")
    case "B" => println("B...")
    case _ =>println("不知道....")
  }

  def judgeGrade(grade:String): Unit ={
    grade match{
      case "A" => println("Excellent...")
      case "B" => println("Good...")
      case "C" => println("Just so so ...")
      case _ => println("You need work harder")
    }
  }
  //judgeGrade("A")
  //judgeGrade("B")
  //judgeGrade("C")
  //judgeGrade("D")
  //双重过滤
  def judgeGrade1(name:String, grade:String): Unit ={
    grade match{
      case "A" => println("Excellent...")
      case "B" => println("Good...")
      case "C" => println("Just so so ...")
      case _ if(name == "ling")=> println("ling is hear!")
      case _ => println("You need work harder")
    }
  }
  //judgeGrade1("张三","A")
  //judgeGrade1("wangwu", "B")
  //judgeGrade1("xiaoer", "C")
  ////双重过滤
  //judgeGrade1("ling", "A+")

  //数组模式匹配
  def greeting(array:Array[String]): Unit ={
    array match {
      case Array("zhangsan") => println("Hi:zhangsan")
      case Array(x, y) => println("Hi:" + x + " , " + y)
      case Array("zhangsan", _*) => println("Hi:zhangsan and other friends")
      case _=> println("Hi:everybody...")
    }
  }
 //greeting(Array( "zhangsan"))
 //greeting(Array("lisi", "zhangsan"))
 //greeting(Array("zhangsan", "xiaoer"))

  //类型匹配
  def matchType(obj:Any): Unit ={
    obj match {
      case x:Int => println("Int")
      case s:String => println("String")
      case m:Map[_,_] => m.foreach(println)
      case _ => println("other type")
    }
  }
 //matchType(1234)
 //matchType(1f)
 //matchType(1.00)
 //matchType(Array(1, 2))
 //matchType(Map("1" -> "2"))
 //matchType(Map("1" -> "2", "3" -> "4"))
 //matchType("hello")

  //异常处理
 //try{
 //  val i = 10 / 0
 //  println(i)
 //} catch {
 //  case e: Exception => println(e.getMessage)
 //  case e: ArithmeticException => println("除数不能为0")
 //}finally {
 //  //释放资源，一定能执行
 //}

  class Person
  case class CTO(name:String, floor:String) extends Person
  case class Employee(name:String, floor:String) extends Person
  case class Other(name:String) extends Person
  def caseclassMatch(person:Person): Unit ={
    person match{
      case CTO(name, floor) => println("CTO name is:" + name + ", floor is:" + floor)
      case Employee(name, floor) => println("Emplyee name is:" + name + ", floor is:" + floor)
      case _ => println("other")
    }
  }
  caseclassMatch(CTO("PK", "22"))
  caseclassMatch(Employee("zhangsan", "18"))
  caseclassMatch(Other("other"))


}

