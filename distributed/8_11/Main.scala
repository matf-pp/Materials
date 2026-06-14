// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    // n odredjuje koliko parcijalnih suma harmonijskog reda ispisujemo.
    val n = StdIn.readInt()
    val conf = new SparkConf()
      .setAppName("HarmonijskiRed")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    // Za svaki i nezavisno racunamo sumu 1 + 1/2 + ... + 1/i.
    val partialSums = sc.parallelize(1 to n)
      .map(i => (1 to i).map(j => 1.0 / j).sum)
      .collect()

    sc.stop()
    println(partialSums.mkString("[", ", ", "]"))
  }
}
