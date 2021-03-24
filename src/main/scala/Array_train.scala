package src.main.scala

object Array_train extends App{
   //println("test")
   //定长数组
   val b = Array("hadoop", "spark", "storm")
   //for(x <- b){println(x)}
   //变长数组
   val c = scala.collection.mutable.ArrayBuffer[Int]()
   c += 2
   println(c)
   c += 3
   println(c)
   c ++= Array(6, 8, 9)
   println(c)
   c.insert(0, 100)
   println(c)
   c.remove(1)
   println(c)
   for (ele <- c) print(ele + ", ")
   println()
   for (i <- (0 until c.length).reverse){
      print(i)
      println(" ," + c(i))
   }
   println(c.mkString)
   val d = c.toArray.mkString
  println(d)

}
