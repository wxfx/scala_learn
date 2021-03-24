object HanniTower extends App{
  def fun1(n:Int, a:Char, b:Char, c:Char): Unit ={
    if(n == 1){
      println("将盘%d从%s移动到%s".format(n, a, c))
      return
    }
    fun1(n-1, a, c, b)
    println("将盘%d从%s移动到%s".format(n, a, c))
    fun1(n-1, b, a, c)
  }
  fun1(3, 'a', 'b', 'c')
}
