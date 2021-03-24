package util

import java.io.InputStreamReader
import java.util.Properties

object PropertiesUtil {
  def main(args: Array[String]): Unit = {

  }
  def load(perpertieName:String):Properties={
    val properties = new Properties()
    properties.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(perpertieName), "UTF-8"))
    properties
  }

}
