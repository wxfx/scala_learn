package src.main.scala

object extends_train {
  def main(args: Array[String]): Unit = {
    class Person{
      var name = ""
      //重写
      override def toString = getClass.getName + "[name=" + name + "]"
    }

    class Employee extends Person{
      var salary = 0.0
      def description = "员工姓名：" + name + "薪水:" + salary
      //重写
     override def toString = super.toString + "[salary=" + salary + "]"
    }
    val emp = new Employee
    emp.salary  = 30000
    emp.name = "keyan"
    //println(emp.description)
    //println(emp.toString)
    ////类型检查和转换
    //println("Hello".isInstanceOf[String])
    //println("Hello".asInstanceOf[String])
    //println(classOf[String])

    class Person1(val name:String, val age:Int){
      override def toString = getClass.getName + "[name=" + name + ",age=" + age + "]"
    }

    class Employee1(name:String, age:Int, val salary:Double) extends Person1(name, age){
      override def toString = super.toString + "[salary=" + salary + "]"
    }
    val emp1 = new Employee1("Tom", 10, 999)
    //println(emp1.name)
    //println(emp1.age)
    //println(emp1.salary)
    //跑不出数据
    //println(emp1.toString)

    class Person2(val name:String, var age:Int){
      println("主构造器已经被调用")
      val school = "五道口职业技术学院"
      def sleep = "8 hours"
      override def toString = "我的学校是：" + school + "我的名字和年龄是：" + name + "," + age
    }
    class Person3(name:String, age:Int) extends Person2(name, age){
      override val school: String = "清华大学"
    }
    //val per3 = new Person3("keyan", 27)
    //println(per3.school)
    //匿名子类
    class Person4(val name:String){
      override def toString = getClass.getName+ "[name=" + name + "]"
    }
    val alien = new Person4("Fred"){
      def greeting = "Greetings, Earthling!My name is Fred."
    }
    //println(alien.greeting)

    //抽象类
    abstract class Person5(val pname:String){
      val id:Int
      var name:String
      def idString:Int
    }

    class Employee5(pname:String) extends Person5(pname){
      val id = 5
      var name = ">>>"
      def idString = pname.hashCode
    }
    val emp5 = new Employee5("keyan")
    println(emp5.name)
    //println(emp5.id)
    //println(emp5.idString)

    class Creature {
      val range:Int = 10
      val env:Array[Int] = new Array[Int](range)
    }

    class Ant extends Creature{
      override val range = 2
    }
    class Ant2 extends{
      override val range = 3
    } with Creature
    val ant = new Ant
    println(ant.range)
    val ant2 = new Ant2
    println(ant2.range)

  }
}
