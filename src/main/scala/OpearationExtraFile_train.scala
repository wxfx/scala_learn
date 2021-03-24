package src.main.scala

import java.io.{FileInputStream, InputStreamReader}

import scala.io.Source
import scala.xml.XML

object OpearationExtraFile_train extends App{
  val file = Source.fromFile("D:/hellospark.txt")
  def readLine(): Unit ={
    for (line <- file.getLines()){
      println(line)
    }
  }
  //readLine()

  def readChar(): Unit ={
    for(ele <- file){
      println(ele)
    }
  }
  //readChar()
  def readNet(): Unit ={
    val file = Source.fromURL("http://www.baidu.com")
    for(line <- file.getLines()){
      println(line)
    }
  }
  //readNet()

  //操作XML文件
  def loadXML(): Unit ={
    val xml = XML.load(
      new InputStreamReader(
        new FileInputStream("D:/default-config.xml")
      )
    )
    println(xml)
  }
  loadXML()
}
