package src.main.scala

object object_train{
  def main(args: Array[String]): Unit = {
    object Dog {
      println("已初始化...")
      private var leg = 0

      def plus() = {
        leg += 1
        leg
      }
    }
    //处理不了
    //val dog1 = new Dog
    //println(dog1.plus)
    //val dog2 = new Dog
    //println(dog2.plus)

    class Cat {
      val hair = Cat.growHair
      private var name = ""

      def changeName(name: String) = {
        this.name = name
      }

      def describe = println("hair:" + hair + ",name:" + name)
    }

    object Cat {
      private var hair = 0

      private def growHair = {
        hair += 1
        hair
      }
    }

    val cat1 = new Cat
    val cat2 = new Cat

    cat1.changeName("黑猫")
    cat2.changeName("白猫")
    //hair:1,name:黑猫
    cat1.describe
    //hair:2,name:白猫
    cat2.describe

    //Apply方法
    class Man private(val sex: String, name: String) {
      def describe = {
        println("Sex:" + sex + ",name:" + name)
      }
    }
    var instance: Man = null

    object Man {
      def apply(name: String
               ) = {
        if(instance == null){
          instance = new Man("男", name)
        }
        instance
      }
    }
    val man1 = Man("Nick")
    val man2 = Man("Thomas")
    man1.describe
    man2.describe
    if (args.length > 0)
      println("Hello," + args(0))
    else
      println("Hello, World!")
    //扩展APP对象报错,需要重载，但重载后也报错

    //枚举
    object TrafficLightColor extends Enumeration{
      var Red = Value(0, "Stop")
      var Yellow = Value(1, "Slow")
      var Green = Value(2, "Go")
    }
    println(TrafficLightColor.Red)
    println(TrafficLightColor.Red.id)
    println(TrafficLightColor.Yellow)
    println(TrafficLightColor.Yellow.id)
    println(TrafficLightColor.Green)
    println(TrafficLightColor.Green.id)
  }
}
