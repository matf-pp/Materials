// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Jednostavno CSV parsiranje je dovoljno za date podatke o zemljotresima.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Zemljotresi")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val averages = sc.textFile("earthquakes.csv")
      .filter(!_.startsWith("id,"))
      .map(columns)
      // Kolona 4 cuva drzavu/saveznu drzavu; zadrzavamo samo zemljotrese u Kaliforniji.
      .filter(row => row.length >= 9 && row(4).equalsIgnoreCase("California"))
      // Za svaku godinu cuvamo zbir magnituda i broj zemljotresa.
      .map(row => (row(8).toInt, (row(1).toDouble, 1L)))
      .reduceByKey { (a, b) => (a._1 + b._1, a._2 + b._2) }
      .map { case (year, (sum, count)) => (year, sum / count) }
      // Najveca prosecna magnituda ide prva; godina razresava izjednacenja.
      .sortBy { case (year, average) => (-average, year) }
      .map { case (year, average) => f"$year $average%.2f" }
      .collect()

    sc.stop()
    if (averages.nonEmpty) println(averages.mkString("\n"))
  }
}
