object Fibonacci extends App{
  def fibonacci1(n:Int): Int ={
    if (n == 1 | n == 2){
      return 1
    }
    fibonacci1(n-1) + fibonacci1(n-2)
  }
  var sum:Int = 2
  var (left:Int, right:Int) = (0, 0)
  def fibonacci2(n:Int):Int={
      if (n < 1){
        println("不存在斐波那契数列！")
        -1
      }
      for(i <- 1 to n){
        if(i == 1) {
          left = 1
          sum = 1
        }else if (i == 2){
          right = 1
          sum = 1
        }else{
          sum = left + right
          left = right
          right = sum
          println(sum)
        }
    }
    sum
  }
  println(fibonacci2(30))
}
