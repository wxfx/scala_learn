package src.main.scala

object trait2_train {
  def main(args: Array[String]): Unit = {
    //富特质
    trait Logger3 {
      def log(msg: String)

      def info(msg: String): Unit = {
        log("INFO:" + msg)
      }

      def warn(msg: String): Unit = {
        log("WARN:" + msg)
      }

      def severe(msg: String): Unit = {
        log("SEVERE:" + msg)
      }
    }

    trait ConsoleLogger3 extends Logger3 {
      def log(msg: String): Unit = {
        println(msg)
      }
    }

    class Account3 {
      protected var balance = 0.0
    }

    abstract class SavingsAccount3 extends Account3 with Logger3 {
      def withdraw(amount: Double): Unit = {
        if (amount > balance) severe("余额不足")
        else balance -= amount
      }
    }
    object Main3 extends App {
      val acct = new SavingsAccount3 with ConsoleLogger3
      acct.withdraw(100)
    }

    trait Logger4{
      def log(msg:String)
    }

    trait ConsoleLogger4 extends Logger4{
      def log(msg:String): Unit ={
        println(msg)
      }
    }

    trait ShortLogger4 extends Logger4{
      val maxLength = 15
      abstract override def log(msg:String): Unit ={
        super.log(
          if(msg.length <= maxLength)
            msg
          else
            s"${msg.substring(0, maxLength - 3)}..."
        )
      }
    }

    class Account4 {
      protected var balance = 0.0
    }

    class SavingsAccount4 extends Account4 with ConsoleLogger4 with ShortLogger4{
      var interest = 0.0
      def withdraw(amount:Double): Unit ={
        if (amount > balance)
          log("余额不足")
        else
          balance -= amount
      }
    }

    object  Main4 extends App{
      val acct = new SavingsAccount4
      acct.withdraw(100)
      println(acct.maxLength)
    }

    //特质中的具体字段
    trait Logger5{
      def log(msg: String)
    }

    trait ConsoleLogger5 extends Logger5{
      def log(msg:String): Unit ={
        println(msg)
      }
    }

    trait ShortLogger5 extends Logger5{
      val maxLength:Int

      abstract override def log(msg:String): Unit ={
        super.log(
          if(msg.length <= maxLength)
            msg
          else
            s"${msg.substring(0, maxLength - 3)}..."
        )
      }
    }

    class Account5{
      protected var balance = 0.0
    }

   abstract class SavingsAccount5 extends Account5 with Logger5{
     var interest = 0.0
     def withdraw(amount:Double): Unit ={
       if (amount > balance)
         log("余额不足")
       else
         balance -= amount
     }
   }

    object Main5 extends App{
      val acct = new SavingsAccount5 with ConsoleLogger5 with ShortLogger5{
        override val maxLength: Int =  20
      }
      acct.withdraw(100)
      println(acct.maxLength)
    }
    //特质构造顺序
    trait Logger6{
      println("我在Logger6特质构造器中,嘿嘿嘿...")
      def log(msg:String)
    }

    trait ConsoleLogger6 extends Logger6{
      println("我在ConsoleLogger6特质构造器中,嘿嘿嘿...")
      def log(msg:String): Unit ={
        println(msg)
      }

    }

    trait ShortLogger6 extends Logger6{
      val maxLength: Int
      println("我在ShortLogger6特质构造器中，嘿嘿嘿...")

      abstract override def log(msg: String){
       super.log(
         if (msg.length <=  maxLength)
           msg
         else
           s"${msg.substring(0, maxLength - 3)}..."
       )
      }

      class Account6{
        println("我在Account6构造器中,嘿嘿嘿...")
        protected var balance = 0.0
      }

      abstract class SavingsAccount6 extends Account6 with ConsoleLogger6 with ShortLogger6{
        println("我再SavingsAccount6构造器中")
        var interest = 0.0
        override val maxLength:Int = 20
        def withdraw(amount:Double): Unit ={
          if(amount > balance)
            log("余额不足")
          else
            balance -= amount
        }
      }

      object Main6 extends App{
        val acct = new SavingsAccount6 with ConsoleLogger6 with ShortLogger6
        acct.withdraw(100)
        println(acct.maxLength)
      }
    }

    import java.io.PrintStream

    trait Logger7{
      def log(msg:String)
    }

    trait FileLogger7 extends Logger7{
      val fileName:String
      val out = new PrintStream(fileName)

      override def log(msg: String): Unit = {
        out.print(msg)
        out.flush()
      }
    }

    class SavingsAccount7{

    }

    object Main7 extends App{
      //val acct = new SavingsAccount7 with FileLogger7{
      //  override val fileName = "20200529.log" //空指针异常
      //}

      //提前定义
      //val acct = new {
      //  override val fileName = "20200629.og"
      //} with SavingsAccounts7 with FileLogger7
      //acct.log("heiheihei")
    }






  }
}
