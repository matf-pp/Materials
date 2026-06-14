// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Osnovno parsiranje kolona za pojednostavljen CSV fajl.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Milijarderi")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val aggregate = sc.textFile("billionaires.csv")
      .filter(!_.startsWith("ime,"))
      .map(columns)
      // Biramo samo evropske milijardere muskog pola iz prvih 50, od 2000. godine nadalje.
      .filter(row =>
        row.length >= 7 &&
          row(1).toInt <= 50 &&
          row(2).toInt >= 2000 &&
          row(5).equalsIgnoreCase("male") &&
          row(6).equalsIgnoreCase("Europe"))
      // Agregat cuva zbir godina i broj izabranih redova.
      .map(row => row(4).toDouble)
      .aggregate((0.0, 0L))(
        (acc, age) => (acc._1 + age, acc._2 + 1),
        (a, b) => (a._1 + b._1, a._2 + b._2)
      )

    sc.stop()
    // Trazeni rezultat je zaokruzena prosecna starost.
    if (aggregate._2 == 0) println("Nema podataka.")
    else println(Math.round(aggregate._1 / aggregate._2))
  }
}
