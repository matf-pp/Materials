// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Ova pomocna funkcija namerno obradjuje samo osnovne kolone razdvojene zarezom.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  // Oznake menjaca kao "6S A" predstavljaju automatski menjac sa 6 brzina.
  def automaticFiveOrMore(transmission: String): Boolean =
    transmission.matches("(?i).*[567]S\\s+A.*")

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Automobili")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val aggregate = sc.textFile("cars.csv")
      .filter(!_.startsWith("pogon,"))
      .map(columns)
      // Zadrzavamo automobile koji zadovoljavaju sve uslove iz teksta zadatka.
      .filter(row =>
        row.length >= 9 &&
          row(0).equalsIgnoreCase("AWD") &&
          automaticFiveOrMore(row(2)) &&
          row(4).equalsIgnoreCase("Gasoline") &&
          row(7).toInt > 2010 &&
          row(8).toInt > 300)
      // Kolona 3 sadrzi potrosnju/efikasnost goriva; agregat racuna prosek.
      .map(row => row(3).toDouble)
      .aggregate((0.0, 0L))(
        (acc, mpg) => (acc._1 + mpg, acc._2 + 1),
        (a, b) => (a._1 + b._1, a._2 + b._2)
      )

    sc.stop()
    // Izbegavamo deljenje nulom ako nijedan red ne prodje filtere.
    if (aggregate._2 == 0) println("Nema podataka.")
    else println(f"${aggregate._1 / aggregate._2}%.6f mpg")
  }
}
