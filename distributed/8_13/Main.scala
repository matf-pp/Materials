import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("BrojanjeCifara")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val counts = sc.textFile("knjiga.txt")
      .flatMap(_.filter(_.isDigit))
      .map(digit => (digit, 1))
      .reduceByKey(_ + _)
      .collectAsMap()

    sc.stop()

    val output = ('0' to '9').map(digit => s"$digit: ${counts.getOrElse(digit, 0)}")
    println(output.mkString("\n"))
  }
}
