package src.main.scala

object List_train extends App{
  val l5 = scala.collection.mutable.ListBuffer[Int]()
  l5 += 2
  println(l5)
  l5 += (1, 3, 5)
  println(l5)
  l5 ++= List(8, 7, 5)
  println(l5)

  l5 -= 2
  println(l5)
  l5 -= (1, 4)
  println(l5)
  l5 --= List(8, 7)
  println(l5)

  println(l5.toList)
  println(l5.toArray)
  println(sum(1, 5, 9))

  def sum(nums: Int*):Int = {
    if (nums.length == 0){
      0
    }else{
      nums.head  + sum(nums.tail:_*)
    }
  }

}
