package src.main.scala

object SimpleObjectApp {
  def main1(args: Array[String]): Unit = {
    val people = new People()
    people.name = "fengchao"
    //println("name:" + people.name + "\n" + "gender:" + people.gender)
    //println("name:" + people.name)

  }
  class People{
    //以下2个都会自动生成get
    //占位符
    var name:String = "_"
    val age:Int = 10

    //这样声明只能在class里面使用
    private [this] val gender:String = "male"

    def printInfo(): Unit ={
      println("gender:" + gender)
    }
  }

  /*
    主构造器:主构造每个类都有主构造器，但并不以this定义
    1、主构造器的参数直接放在类名之后，只有主构造器的参数才能变成类定义中的字段，它们初始化的值为构造时传入的参数
    2、主构造器会执行类定义中的所有语句
    3、如果name不加var/val，则对象访问不到name，可以测下的
    附属构造器:用this声明，可以写多个，只要参数不一样就行，可以分析SparkContext类
    1、附属构造器的第一行代码必须要调用主构造器或者其它附属构造器
   */
  class Person(val name:String, val age:Int) {
    println("进入主构造器!")
    val school: String = "YangtzeUniversity"
    var gender:String = "_"
    println("离开主构造器")

    //附属构造器
    def this(name:String, age:Int, gender:String){
      this(name, age)
      this.gender = gender
    }
  }

  /*
    继承：会先执行父类的构造器
    1、major是子类特有的，所以必须得加val/var关键字才能访问
    重写：一定要有override关键字,如果不重写，则父类的变量必须声明为var
   */
  class Student(name:String, age:Int, var major:String) extends Person(name, age){
    println("子类Student进入")
    override val school = "社会大学"
    //school = "社会大学"
    println("子类Student out")
  }

  /*
    抽象:必须用abstract声明,类的一个或者多个方法没有完整的实现（只有定义，没有实现）,必须子类实现才能使用
    1、方法和属性必须全部实现，不然报错
   */
  abstract class AbsPerson{
    def speak
    val name:String
    val age:Int
  }
  class Mom extends AbsPerson{
    override def speak: Unit = {
      println("speak")
    }

    override val name: String = "PengShiLian"
    override val age: Int = 52
  }

  /*
    伴生类和伴生对象：如果有一个class，还有一个与class（类）同名的object(对象)
    则成object为class的伴生对象，class为object的伴生类，两个互为伴生
   */
  //伴生类
  class Ban{
    println("class Ban enter.....")
    println("class Ban leave.....")
  }
  //伴生对象:本身是一个单例对象
  object Ban{
    println("Pan object 进入")

    var count = 0

    def inct = {
      count += 1
    }

    //最佳实践:在object的apply方法中去new Class
    def apply() = {
      println("Object Ban apply....")
      new Ban
    }

  }
  def main(args: Array[String]): Unit = {
    //主构造器测试
    //val ali = new Person("ali", 22)
    //println(ali.name + ":" +ali.age + ":" + ali.school + ":" + ali.gender)

    //附属构造器测试
    //val jd = new Person(name="jd", age=20, gender="male")
    //println(jd.name + ":" +jd.age + ":" + jd.school + ":" + jd.gender)

    //继承测试
    //val pdd = new Student("pdd", 5, "pingtuan")
    //println(pdd.name + ":" + pdd.age + ":" + pdd.school + ":" + pdd.gender + ":" + pdd.major)

    //多态测试
    //val mom = new Mom
    //mom.speak

    //伴生类和伴生对象测试
    //伴生对象，调用Apply方法
    val ban = Ban()//object.apply
    println("~~~~~~~~~~~~~")
    val ban1 = new Ban()
    println(ban1)
    //这个没测试成功！
    //ban1()
  }


}


