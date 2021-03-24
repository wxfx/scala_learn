package src.main.scala

object clsss_train {
  def main(args: Array[String]): Unit = {
    //class Dog{
    //  //private var leg = 4
    //  var leg = 4
    //  def shout(content:String): Unit ={
    //    println(content)
    //  }
    //  def  currentLeg = leg
    //}

    //val dog = new Dog
    ////dog shout "汪汪汪"
    ////println(dog currentLeg)
    ////getter
    ////println(dog leg)
    ////setter
    //dog.leg = (10)
    ////println(dog currentLeg)

   //lass Dog2{
    // private var _leg = 4
    // def leg = _leg
    // //这里会报错，不能有空格
    // //def leg_ = (newLeg:Int){
    // def leg_=(newLeg:Int){
    //   _leg = newLeg
    // }
   }//
    //val dog2 = new Dog2
    //dog2.leg_=(10)
    ////println(dog2.leg)

    //import scala.beans.BeanProperty

    //class Person{
    //  @BeanProperty var name: String=_
    //}
    //var person = new Person
    //person.setName("Jordan")
    //person.getName
    ////println(person.name)

    //class ClassConstructor(var name:String, private var price: Double){
    //  def myPrintLn = println(name + "," + price)
    //}
    //var cls = new ClassConstructor("《西游记》", 90.0)
    //cls.myPrintLn
    //class ClassConstructor2(var name:String="", private var price: Double=0){
    //  def myPrintLn = println(name + "," + price)
    //}
    //var cls2 = new ClassConstructor2()
    //cls2.myPrintLn
    //var cls2_2 = new ClassConstructor2("《红楼梦》", 80)
    //cls2_2.myPrintLn

    //class Person2 private {
    // private var name = ""
    // private var age = 0

    //  def this(name:String){
    //    this()
    //    this.name = name
    //  }
    //  def this(name:String, age:Int){
    //    this(name)
    //    this.age = age
    //  }
    //  def description = name + " is" + age + " years old"

    //}
    //var per = new Person2("Keyan", 28)
    ////println(per.description)

    //import scala.collection.mutable.ArrayBuffer
    ////嵌套类
    //class Network{
    //  class Member(val name:String){
    //    val contacts = new ArrayBuffer[Member]
    //  }
    //  private val members = new ArrayBuffer[Member]
    //  def join(name: String) = {
    //    val m = new Member(name)
    //    members += m
    //    m
    //  }
    //  val chatter1 = new Network
    //  val chatter2 = new Network

    //  //Fred 和Wilma加入局域网1
    //  val fred = chatter1.join("Fred")
    //  val wilma = chatter1.join("Wilma")
    //  //Barnet加入局域网
    //  val barney = chatter2.join("Barney")


    //  //Fred将同属于局域网1中的Wilma添加为联系人
    //  fred.contacts += wilma
      //报错，证明了Scala中内部类从属于实例并不从属于外部类
      //fred.contacts += barney
    //}
  //}
  import scala.collection.mutable.ArrayBuffer
  //伴生对象
  class Network2 {
    private val members = new ArrayBuffer[Network2.Member]
    def join(name:String) = {
      val m = new Network2.Member(name)
      members += m
      m
    }
    def description = "该局域网中的联系人：" + (for (m <- members) yield m.description).mkString(",")
  }

  object Network2{
    class Member(val name:String){
      val contacts = new ArrayBuffer[Member]
      def description = name + "的联系人:" + (for(c<-contacts) yield c.name).mkString("")
    }
  }

  val chatter3 = new Network2
  val chatter4 = new Network2

  //Fred和Wilma加入局域网1
  val fred2 = chatter3.join("Fred")
  val wilma2 = chatter3.join("Wilma")
 //Barney加入局域网2
  val barney2 = chatter4.join("Barney")
  //Fred将同属于局域网3中的Wilma添加为联系人
  fred2.contacts += wilma2
  //Fred将不属于局域网3中，属于局域网4中的Wilma添加为联系人
  fred2.contacts += barney2

  //println(chatter3.description)
  //println(chatter4.description)
  //println(fred2.description)
  //println(wilma2.description)
  //println(barney2.description)

  class Network3 {
    class Member(val name: String){
      val contacts = new ArrayBuffer[Network3#Member]
    }

    private val members = new ArrayBuffer[Member]

    def join(name:String)={
      val m = new Member(name)
      members += m
      m
    }
  }

  val chatter5 = new Network3
  val chatter6 = new Network3

  //Fred和Wilma加入局域网1
  val fred3 = chatter5.join("Fred")
  val wilma3 = chatter5.join("Wilma")
  //Barney加入局域网2
  val barney3 = chatter6.join("Barney")
  fred3.contacts += wilma3
}

package society {
  package professional {
    class Executive {
      private[professional] var workDetails = null
      private[society] var friends = null
      private[this] var secrets = null

      def help(another: Executive): Unit ={
        println(another.workDetails)
        //报错，访问不到
        //println(another.secrets)
      }
    }

  }
}
