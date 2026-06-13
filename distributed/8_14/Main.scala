import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("SparkGreske")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val errors = sc.textFile("log.txt")
      .filter(line => line.startsWith("[error]") && line.toLowerCase.contains("spark"))
      .collect()

    sc.stop()
    if (errors.nonEmpty) println(errors.mkString("\n"))
  }
}
