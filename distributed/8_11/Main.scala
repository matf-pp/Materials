import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    val n = StdIn.readInt()
    val conf = new SparkConf()
      .setAppName("HarmonijskiRed")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val partialSums = sc.parallelize(1 to n)
      .map(i => (1 to i).map(j => 1.0 / j).sum)
      .collect()

    sc.stop()
    println(partialSums.mkString("[", ", ", "]"))
  }
}
