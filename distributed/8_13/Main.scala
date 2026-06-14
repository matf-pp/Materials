// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def main(args: Array[String]): Unit = {
    // Brojimo samo karaktere koji su cifre; sva slova i interpunkcija se ignorisu.
    val conf = new SparkConf()
      .setAppName("BrojanjeCifara")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val counts = sc.textFile("knjiga.txt")
      // Jedna linija moze imati vise cifara, zato flatMap od linije pravi vise karaktera.
      .flatMap(_.filter(_.isDigit))
      .map(digit => (digit, 1))
      // reduceByKey sabira jedinice za istu cifru.
      .reduceByKey(_ + _)
      .collectAsMap()

    sc.stop()

    // Ispisujemo sve cifre od 0 do 9, ukljucujuci one koje se ne pojavljuju.
    val output = ('0' to '9').map(digit => s"$digit: ${counts.getOrElse(digit, 0)}")
    println(output.mkString("\n"))
  }
}
