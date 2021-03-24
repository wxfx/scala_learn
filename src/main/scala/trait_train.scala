package src.main.scala

object trait_train {
  def main(args: Array[String]): Unit = {
    trait Logger{
      def log(msg: String)
    }

    class ConsoleLogger extends Logger with Cloneable with Serializable{
      def log(msg: String): Unit ={
        println(msg)
      }
    }

    val con1 = new ConsoleLogger
    //con1.log("Hello")

    trait ConsoleLogger2{
      def log(msg: String): Unit ={
        println(msg)
      }
    }

    class Account{
      protected var balance = 0.0
    }

    class SavingsAccount extends Account with ConsoleLogger2{
      def withdraw(amount:Double): Unit ={
        if (amount > balance)
          log("余额不足")
        else
          balance -= amount
      }
    }

    val save = new SavingsAccount
    //save.withdraw(100)

    trait ConsoleLogger3 extends Logger{
      def log(msg:String): Unit ={
        println(msg)
      }
    }
    class Account3{
      protected var balance = 0.0
    }

    abstract class SavingsAccount3 extends Account with Logger{
      def withdraw(amount:Double): Unit ={
        if(amount > balance)
          log("余额不足")
        else
          balance -= amount
      }
    }


    object Main extends App{
      val account = new SavingsAccount3 with ConsoleLogger3
      account.withdraw(100)
    }

    //叠加在一起的特质

    trait TimestampLogger extends ConsoleLogger3{
      override def log(msg:String): Unit ={
        super.log(new java.util.Date() + " " + msg)
      }
    }

    trait ShortLogger extends ConsoleLogger3{
      override def log(msg: String): Unit ={
        super.log(
          if (msg.length <= 15)
            msg
          else
            s"${msg.substring(0, 12)}..."
        )
      }
    }

    class Account4{
      protected  var balance = 1.0
    }

    abstract class SavingsAccount4 extends Account3 with Logger{
      def withdraw(amount:Double): Unit ={
        if (amount > balance)
          log("余额已经不足了")
        else
          balance -= amount
      }

    }

      val acct1 = new SavingsAccount4 with TimestampLogger with ShortLogger
      val acct2 = new SavingsAccount4 with ShortLogger with TimestampLogger
      acct1.withdraw(100)
      acct2.withdraw(222)

  }
}
