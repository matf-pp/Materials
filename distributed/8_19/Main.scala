// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Za pripremljen ulaz dovoljan je osnovni CSV parser.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("OscarDobitnici")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val winners = sc.textFile("oscar.csv")
      .filter(!_.startsWith("Index,"))
      .map(columns)
      // Godina je u koloni 1, a starost u koloni 2 u ovom pojednostavljenom skupu podataka.
      .filter(row => row.length >= 5 && row(1).toInt >= 1980 && row(1).toInt <= 1990 && row(2).toInt > 40)
      // Godinu koristimo kao kljuc da sortByKey ispise dobitnike hronoloski.
      .map(row => (row(1).toInt, s"${row(3)} (${row(4)})"))
      .sortByKey()
      .values
      .collect()

    sc.stop()
    if (winners.nonEmpty) println(winners.mkString("\n"))
  }
}
