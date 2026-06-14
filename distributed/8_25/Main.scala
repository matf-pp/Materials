// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Jednostavne CSV redove delimo u ociscene kolone.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("CovidSmrtnost")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val rates = sc.textFile("covid.csv")
      .filter(!_.startsWith("Day,"))
      .map(columns)
      .filter(_.length >= 9)
      // Grupisemo po godini, kodu drzave i nazivu drzave; sabiramo slucajeve i smrti.
      .map(row => ((row(2).toInt, row(6), row(5)), (row(3).toLong, row(4).toLong)))
      .reduceByKey { (a, b) => (a._1 + b._1, a._2 + b._2) }
      .sortBy { case ((year, code, _), _) => (year, code) }
      .map { case ((year, _, country), (cases, deaths)) =>
        // Stopa smrtnosti je procenat smrti medju prijavljenim slucajevima.
        val percent = if (cases == 0) 0.0 else deaths * 100.0 / cases
        f"$year $country $percent%.2f%%"
      }
      .collect()

    sc.stop()
    if (rates.nonEmpty) println(rates.mkString("\n"))
  }
}
