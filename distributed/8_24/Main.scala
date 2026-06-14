// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Primer ulaza koristi jednostavne kolone razdvojene zarezom.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("EmisijePoDecenijama")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val totals = sc.textFile("emissions.csv")
      .map(columns)
      // Preskacemo redove cija kolona godine nije broj, ukljucujuci zaglavlje.
      .filter(row => row.length >= 5 && row(1).forall(_.isDigit))
      .map { row =>
        val year = row(1).toInt
        val decade = (year / 10) * 10
        // Kljuc je par drzava-decenija, a vrednost su emisije tri gasa.
        ((row(0), decade), (row(2).toLong, row(3).toLong, row(4).toLong))
      }
      // Sabiramo emisije za redove sa istom drzavom i decenijom.
      .reduceByKey { (a, b) => (a._1 + b._1, a._2 + b._2, a._3 + b._3) }
      .sortBy { case ((country, decade), _) => (country, decade) }
      .map { case ((country, decade), (co2, n2o, ch4)) =>
        s"$country (${decade}s) | CO2: $co2; N2O: $n2o; CH4: $ch4"
      }
      .collect()

    sc.stop()
    if (totals.nonEmpty) println(totals.mkString("\n"))
  }
}
