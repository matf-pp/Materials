import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("OscarDobitnici")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val winners = sc.textFile("oscar.csv")
      .filter(!_.startsWith("Index,"))
      .map(columns)
      .filter(row => row.length >= 5 && row(1).toInt >= 1980 && row(1).toInt <= 1990 && row(2).toInt > 40)
      .map(row => (row(1).toInt, s"${row(3)} (${row(4)})"))
      .sortByKey()
      .values
      .collect()

    sc.stop()
    if (winners.nonEmpty) println(winners.mkString("\n"))
  }
}
