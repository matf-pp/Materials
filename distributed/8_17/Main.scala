import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    val brand = StdIn.readLine().trim
    val conf = new SparkConf()
      .setAppName("NasumicneTransakcije")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val transactions = sc.textFile("uredjaji.txt")
      .filter { line =>
        val first = line.trim.split("\\s+", 2).headOption.getOrElse("")
        first.equalsIgnoreCase(brand)
      }
      .takeSample(withReplacement = false, num = 5, seed = System.currentTimeMillis())

    sc.stop()
    if (transactions.nonEmpty) println(transactions.mkString("\n"))
  }
}
