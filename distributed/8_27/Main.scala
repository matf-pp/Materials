import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ProdajaAlbuma")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val averages = sc.textFile("albums.csv")
      .filter(!_.startsWith("izvodjac,"))
      .map(columns)
      .filter(row => row.length >= 6 && row(4).equalsIgnoreCase("Rock") && row(5).toInt <= 100)
      .map(row => (row(3).toInt, (row(2).toDouble, 1L)))
      .reduceByKey { (a, b) => (a._1 + b._1, a._2 + b._2) }
      .sortByKey()
      .map { case (year, (sum, count)) => f"$year ${sum / count}%.2f" }
      .collect()

    sc.stop()
    if (averages.nonEmpty) println(averages.mkString("\n"))
  }
}
