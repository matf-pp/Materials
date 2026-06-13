import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    val n = StdIn.readInt()
    val conf = new SparkConf()
      .setAppName("Faktorijeli")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val factorials = sc.parallelize(1 to n)
      .map(x => (1 to x).foldLeft(BigInt(1))((acc, value) => acc * value))
      .collect()

    sc.stop()
    println(factorials.mkString("[", ", ", "]"))
  }
}
