import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ZbirVektora")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val v1 = sc.textFile("vektor1.txt")
      .flatMap(_.split(",\\s*"))
      .filter(_.nonEmpty)
      .map(_.toInt)
    val v2 = sc.textFile("vektor2.txt")
      .flatMap(_.split(",\\s*"))
      .filter(_.nonEmpty)
      .map(_.toInt)

    val sum = v1.zip(v2)
      .map { case (a, b) => a + b }
      .collect()

    sc.stop()
    println(sum.mkString("[", ", ", "]"))
  }
}
