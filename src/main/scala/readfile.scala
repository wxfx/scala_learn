import java.io.File

object readfile {
  def main(args: Array[String]): Unit = {
    //val file = Source.fromFile("F:\\学习资料\\慕课网\\SparkSQL极速入门 整合Kudu实现广告业务数据分析")
    val file = new File("F:\\学习资料\\慕课网\\SparkSQL极速入门 整合Kudu实现广告业务数据分析")
    readf(file)
  }
  def readf(dir:File): Unit ={
    val children = dir.listFiles.map(file=>{
      if(file.isDirectory) {
        println(file.getAbsolutePath)
        readf(file)
      }else{
        println(file.getName.replace(".ev4", ""))
        file.getAbsolutePath
      }
    })
  }
}
