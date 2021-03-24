object DimidiateSearch extends App{
  //val arr = (2, 4, 5, 8)
  val arr = Array(1, 2, 4 ,5 ,6, 10, 88,120, 333)
  arr.foreach{a=>println(a)}
  search(0, arr.length-1, arr, 5)
  def search(left:Int, right:Int, arr:Array[Int], target:Int): Unit ={
     var mid = (left + right) / 2
    if (left == right & arr(mid) != target)
      println("元素不存在")
    if(arr(mid) == target){
      println("序号是：" + mid)
     } else if (arr(mid) < target){
       search(mid+1, right, arr, target)
     } else if (arr(mid) > target){
       search(left, mid-1, arr, target)
     }
  }
}
