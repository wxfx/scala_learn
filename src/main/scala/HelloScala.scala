package src.main.scala


object HelloScala {
  def main(args: Array[String]): Unit = {
      //println("HelloScala!")
    //var result = 0
    //val op:Char='_'
    //op match {
    //  case '+'=>result = 1
    //  case '_'=>result = -1
    //  case _ =>result = 0
    //}
    //println(result)
    //守卫
    //for(ch<-"+-3!"){
    //  var sign = 0
    //  var digit = 0

    //  ch match {
    //    case '+'=>sign = 1
    //    case '-'=>sign = -1
    //    //case _ if ch.toString.equals("3")=>digit = 3
    //    case ch if Character.isDigit(ch)=>digit=Character.digit(ch,10)
    //    //case _=>sign = 0
    //    case _=>
    //  }
    //  println(ch + " " + sign + " " + digit)
    //类型模式
    //val a = 8
    //val obj = if(a == 1) 1
    //else if(a == 2) "2"
    //else if(a == 3) BigInt(3)
    //else if(a == 4) Map("aa"->1)
    //else if(a == 5) Map(1 -> "aa")
    //else if(a == 6) Array(1, 2, 3)
    //else if(a == 7) Array("aa", 1)
    //else if(a == 8) Array("aa")
    //println(obj)
    //val r1 = obj match {
    //  case x: Int => x
    //  case s:String =>s.toInt
    //  case BigInt => Int.MaxValue
    //  case m:Map[String, Int] => "Map[String, Int]类型的Map集合"
    //  case a:Map[_,_] => "Map集合"
    //  case a:Array[Int]=> "It's an Array[Int]"
    //  case a:Array[String] => "It's an Array[String]"
    //  case a:Array[_]=>"It's an array of something other than Int"
    //  case _=>0
    //}
    //println(r1 + "," + r1.getClass.getName)
    // 匹配数组
    //for(arr <- Array(Array(0), Array(1, 0), Array(0, 1, 0), Array(1, 1, 0), Array(1, 1, 0, 1))){
    //  val result = arr match {
    //    case Array(0) => "0"
    //    case Array(x, y) => x + "," + y
    //    case Array(x, y, z) => x + "," + y + "," + z
    //    case Array(0, _*) => "0..."
    //    case _=>"something else"
    //  }
    //  println(result)
    //匹配列表
    //val arr = Array(List(0), List(1, 0), List(0, 0, 0), List(1, 0, 0))
    //for(lst <- arr){
    //  val result = lst match {
    //    case 0 :: Nil => "0"
    //    case x :: y ::Nil =>x + "," + y
    //    case 0 :: tail => "0..."
    //    case _=>"something else"
    //  }
    //  println(result)
    //匹配元组
    //for(pair <- Array((0, 1), (1, 0), (1, 1))){
    //  val result = pair match {
    //    case (0, _) => "0..."
    //    case (y, 0) => y + ", 0"
    //    case _=>"neither is 0"
    //  }
    //  println(result)
    //提取器
    //创建object Square
   //object Square{
   //   def unapply(arg: Double): Option[Double] = Some(math.sqrt(arg))
   // }
   // val number:Double = 36.0
   // number match {
   //   case Square(n) => println(s"square root of $number is $n")
   //   case _ => println("nothing matched")
   // }
  //object Names{
  //    def unapply(arg: String): Option[Seq[String]] = {
  //      if (arg.contains(","))
  //       Some(arg.split(","))
  //      else
  //        None
  //    }
  //  }
  //  val namesString = "a,b,c"
  //  namesString match {
  //    case Names(x)=>{
  //      println("the string contains three digits")
  //      println(s"$x")
  //      //println(s"$x $y $z")
  //    }
  //    case _ => println("nothing matched")
  //  }
    //变量中声明的模式
    //val (x, y) = (1, 2)
    ////这个写法挺溜的
    //val (q, r) = BigInt(10) /% 3
    //val arr = Array(1, 7, 2, 9)
    //val Array(a, b , _*) = arr
    //println(x, y)
    //println(q, r)
    //println(a, b)

    //for表达式中的模式
    //import scala.collection.JavaConversions._
    //for((k, v) <- System.getenv().as)
    //  println(k + "->" + v)
    //样例类
    //abstract class Amount
    //  case class Dollar(value: Double) extends Amount
    //  case class Currency(value: Double, unit: String) extends Amount
    //  case object Nothing extends Amount
  //val arr = Array(Dollar(1000.0), Currency(9999.0, "EUR"), Nothing)
  //for (amt <- arr){
  //  val result = amt match {
  //    case Dollar(v) => "$" + v
  //    case Currency(_, u) => u
  //    case Nothing => ""
  //  }
  //  println(amt + ":" + result)
  //}
  //val amt = Currency(29.09, "Asia")
  //val price = amt.copy(value = 20.00)
  //  println(amt)
  //  println(price)
  //  println(amt.copy(unit = "CHF"))
    //List(1, 9, 2, 8) match {
    //  case first :: second :: rest =>
    //    println(first + second + rest.length)
    //  case _ => 0
    //}
    //匹配嵌套结构
    //abstract class Item
    //case class Article(decription: String, price: Double) extends Item
    //case class Bundle(description: String, discount: Double, item: Item*) extends Item

    //val sale = Bundle("愚人节大甩卖系列", 10,
    //  Article("《九阴真经》", 40),
    //  Bundle("从出门一条狗到装备齐全发光的修炼之路系列", 20,
    //  Article("《如何快速捡起地上的装备》", 80),
    //  Article("《名字起得太长躲在树后被地方发现》", 30)))
    //val result1 = sale match {
    //  case Bundle(_,_,Article(descr,_),_*) => descr
    //}
    //println(result1)
    //val result2 = sale match {
    //  case Bundle(_,_,art@Article(_,_), rest@_*) =>(art, rest)
    //}
    //println(result2)
    //val result3 = sale match {
    //  case Bundle(_,_, art@Article(_,_),rest) =>(art, rest)
    //}
    //println(result3)
    //def price(it:Item): Double ={
    //  it match {
    //    case Article(_, p) =>p
    //    case Bundle(_, disc, its@_*) => its.map(price _).sum-disc
    //  }
    //}
    //println(price(sale))
    // 模拟枚举
    //sealed abstract class TrafficLightColor
    //case object Red extends TrafficLightColor
    //case object Yellow extends TrafficLightColor
    //case object Green extends TrafficLightColor

    //for(color <- Array(Red, Yellow, Green))
    //  println(
    //    color match {
    //      case Red => "stop"
    //      case Yellow => "slowly"
    //      case Green => "go"
    //    }
    //  )
    //val f: PartialFunction[Char, Int] ={
    //  case '+' => 1
    //  case '-' => -1
    //}
    //println(f('-'))
    //println(f.isDefinedAt('0'))
    //println(f('+'))
    ////报错
    ////println(f('0'))
    //val f1 = new PartialFunction[Any, Int] {
    //  def apply(any: Any) = any.asInstanceOf[Int] + 1
    //  def isDefinedAt(any: Any) = if (any.isInstanceOf[Int]) true else false
    //}
    //val rf1 = List(1, 3, 6, "seven") collect f1
    //println(rf1)

    //def f2: PartialFunction[Any, Int] = {
    //  case i: Int => i + 1
    //}
    //val rf2 = List(1, 2, 3, "four") collect f2
    //println(rf2)

    //作为参数的函数
    //def plus(x: Int) = 3 + x
    //val result1 = Array(1, 2, 3, 5).map(plus(_))
    //println(result1.mkString(","))

    //匿名函数
    //val triple = (x: Double) => 3 * x
    //println(triple(3))

    //高阶函数
    //def highOrderFunction1(f: Double => Double) = f(10)
    //def minus7(x:Double) = x - 7
    //val result2 = highOrderFunction1(minus7)
    //println(result2)

    //def minusxy(x: Int) = (y:Int) => x - y
    //val result3 = minusxy(3)(5)
    //println(result3)
    ////参数（类型）推断
    //highOrderFunction1((x:Double) => 3*x)
    //highOrderFunction1((x)=>3*x)
    //highOrderFunction1(x=>3*x)
    //highOrderFunction1(3 * _)
    //def minusxy(x:Int) = (y:Int) => x - y
    //val f1 = minusxy(10)
    //val f2 = minusxy(10)
    //print(f1(3) + f2(3))
    //def mul(x:Int, y:Int) = x * y
    //println(mul(3, 8))

    //def mulCurry(x: Int) = (y: Int) => x * y
    //println(mulCurry(10)(9))

    //def mulCurry2(x:Int)(y:Int) = x * y
    //println(mulCurry2(10)(8))

    //val a = Array("Hello", "Ling")
    //val b = Array("hello", "ling")
    //println(a.corresponds(b)(_.equalsIgnoreCase(_)))
    //控制抽象
    //def runInThread(f1(): => Unit):Unit={
    //  new Thread{
    //    override def run():Unit ={
    //      f1()
    //    }
    //  }.start()
    //}

    //runInThread{
    //  ()=>println("开始咯！")
    //    Thread.sleep(3000)
    //    println("结束咯！")
    //}
    //def runInThread(f1: => Unit):Unit={
    //  new Thread{
    //    override def run():Unit ={
    //      f1
    //    }
    //  }.start()
    //}

    //runInThread{
    //  println("开始咯！")
    //  Thread.sleep(3000)
    //  println("结束咯！")
    //}

    def until(condition: => Boolean)(block: =>Unit): Unit ={
      if(!condition){
        block
        until(condition)(block)
      }
    }
    var x = 10
    until(x == 0){
      x -= 1
      println(x)
    }
  }
}
