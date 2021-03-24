package src.main.scala

import com.sun.javafx.util.Logging

object OOP_train {
  def main(args: Array[String]): Unit = {
    //val people = new People
    //people.name = "Jordan"
    ////无法赋值
    ////people.age = 59
    //println(people.name)
    //println(people.printInfo())
    //主构造器
    //val p1 = new Person("keyan", 27)
    //println(p1.name + " : " + p1.age + " : " + p1.school)

    ////附属构造器
    //val p2 = new Person("Jordan", 50, "M")
    //println(p2.name + " : "
    //+ p2.age + " : "
    //+ p2.school + " :"
    //+ p2.gender)

    //继承,会先执行父类的构造器
    //val s1 = new Student("Keyan", 28, "Computer")
    //println("name: " + s1.name + " : " + s1.age + " ：" + s1.major + " : " + s1.gender)
    //println(s1.toString)
    //val s2 = new Student2
    //s2.speak
    //object.apply
    val a = ApplyTest()
    println("~~~~~~~~~")
    val c = new ApplyTest()
    println(c)
    //c()
    //class.apply
  }

}

class People{
  var name:String = _
  val age:Int = 10
  private [this] val gender = "male"
  def printInfo() = {
    println("gender: " + gender)
  }
}

//主构造器
//将val去掉无影响
class Person(val name:String, val age:Int){
  println("Person Constructor enter....")
  val school = "Peking"
  var gender:String = _
  println("Person Constructor leave....")
  //附属构造器
  def this(name:String, age:Int, gender:String){
    //附属构造器的第一行必须要调用主构造器或者其它附属构造器
    this(name, age)
    this.gender = gender
  }
}

//继承
class Student(name:String, age:Int, var major:String) extends Person(name, age){
  println("Person Student enter....")

  //重写
  override val school: String = "Yangtezu University"
  override def toString:String = "Person: override def toString:" + school
  println("Person Student leave....")
}

abstract class Person2{
  def speak
  val name:String
  val age:Int
}

//属性必须全部实现, 不然报错
class Student2 extends Person2{
  override def speak: Unit = {
    println(name + age + "speak")
  }
  override val name:String = "Ling"
  override val age:Int = 19
}

class ApplyTest{
  println("class ApplyTest enter.....")
  println("class ApplyTest leave.....")
}
object ApplyTest{
  println("Object ApplyTest enter.....")
  var count = 0
  def incr = {
    count += 1
  }

  //最佳实践:在Object中的apply方法中去new Class
  def apply() = {
    println("Object ApplyTest apply....")
    //在objecd中apply中new class
    new ApplyTest
  }

  class SparkConf1(loadDefaults:Boolean) extends Cloneable with Serializable{

  }

}
