// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def main(args: Array[String]): Unit = {
    // Oba vektora se ucitavaju kao nizovi brojeva iz tekstualnih datoteka.
    val conf = new SparkConf()
      .setAppName("ZbirVektora")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    // flatMap razbija jednu liniju oblika "1, 2, 3" na pojedinacne elemente.
    val v1 = sc.textFile("vektor1.txt")
      .flatMap(_.split(",\\s*"))
      .filter(_.nonEmpty)
      .map(_.toInt)
    val v2 = sc.textFile("vektor2.txt")
      .flatMap(_.split(",\\s*"))
      .filter(_.nonEmpty)
      .map(_.toInt)

    // zip spaja elemente na istim pozicijama; zatim sabiramo svaki par.
    val sum = v1.zip(v2)
      .map { case (a, b) => a + b }
      .collect()

    sc.stop()
    println(sum.mkString("[", ", ", "]"))
  }
}
