package util

import io.searchbox.client.{JestClient, JestClientFactory}
import io.searchbox.client.config.HttpClientConfig
import io.searchbox.core.{Bulk, Index, Search}
import org.elasticsearch.index.query.{BoolQueryBuilder, MatchQueryBuilder, RangeQueryBuilder}
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.highlight.HighlightBuilder
import org.elasticsearch.search.sort.SortOrder

import scala.collection.mutable.ListBuffer

object MyEsUtil {
  private var factory: JestClientFactory = null

  def getClient = {
    if (factory == null)
      build()
    factory.getObject
  }

  def build() = {
    factory = new JestClientFactory
    factory.setHttpClientConfig(
      new HttpClientConfig.Builder("http://master:9200")
        .multiThreaded(true)
        .maxTotalConnection(20)
        .connTimeout(10000)
        .readTimeout(1000)
        .build()
    )
  }

  //batch存储
  def saveBulk(dataList:List[(String, AnyRef)], indexName:String)={
    if (dataList != null  && dataList.size > 0) {
      val jest:JestClient = getClient
      val bulkBuilder = new Bulk.Builder()
      bulkBuilder.defaultIndex(indexName).defaultType("_doc")
      for((id, data) <- dataList){
        val index = new Index.Builder(data).id(id).build()
        bulkBuilder.addAction((index))
      }

      val bulk = bulkBuilder.build()
      val items = jest.execute(bulk).getItems
      println("已保存: " + items.size() + "条数据!")
      jest.close()
    }
  }


  def main(args: Array[String]): Unit = {
    val jest = getClient
    //添加数据,如果没有这个索引，会新建
    //val search = new Index.Builder(Movie(4, "两只老虎", 9.0)).index("movie1111_index").`type`("movie").build()
    //1、查询数据
    //val query = "{\n  \"query\": {\n    \"match\": {\n      \"name\": \"红海行动\"\n    }\n  }\n}"

    //2、查询数据，官方推荐
    val searchBulider = new SearchSourceBuilder
    val boolbuilder = new BoolQueryBuilder()
    boolbuilder.should(new MatchQueryBuilder("name", "红海战役"))
      .filter(new RangeQueryBuilder("doubanScore").gte(3))
    searchBulider.query(boolbuilder)
    searchBulider.sort("doubanScore", SortOrder.ASC)
    searchBulider.from(0)
    searchBulider.size(20)
    searchBulider.highlight(new HighlightBuilder().field("name"))
    val query2 = searchBulider.toString
    print(query2)

    val search = new Search.Builder(query2).addIndex("movie1111_index").addType("movie").build()

    val result = jest.execute(search)
    val hitList = result.getHits(classOf[Movie])
    val resultList = new ListBuffer[Movie]
    import scala.collection.JavaConversions._
    for (hit <- hitList){
      val source = hit.source
      resultList.add(source)
    }
    println("~~~~~~~~~~~~~~~~~~~~")
    print(resultList.mkString("\n"))
    jest.close()

  }
  case class Movie(id: Long, name: String, doubanScore:Double ) {}

}
