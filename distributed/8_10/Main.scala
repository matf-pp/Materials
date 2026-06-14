// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    // n je broj cije faktorijele racunamo: 1!, 2!, ..., n!.
    val n = StdIn.readInt()
    val conf = new SparkConf()
      .setAppName("Faktorijeli")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    // Svaki broj iz intervala obradjuje se nezavisno, pa ga mozemo mapirati u njegov faktorijel.
    val factorials = sc.parallelize(1 to n)
      // foldLeft mnozi sve brojeve od 1 do x i koristi BigInt da izbegne brzo prekoracenje Int-a.
      .map(x => (1 to x).foldLeft(BigInt(1))((acc, value) => acc * value))
      .collect()

    sc.stop()
    println(factorials.mkString("[", ", ", "]"))
  }
}
