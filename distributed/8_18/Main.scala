import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("CentroidPolisa")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val aggregate = sc.textFile("insurance.csv")
      .filter(!_.startsWith("ID,"))
      .map(columns)
      .filter(row =>
        row.length >= 7 &&
          row(2).equalsIgnoreCase("PALM BEACH COUNTY") &&
          row(5).equalsIgnoreCase("Residential") &&
          row(6).equalsIgnoreCase("Wood"))
      .map(row => (row(3).toDouble, row(4).toDouble, 1L))
      .aggregate((0.0, 0.0, 0L))(
        (acc, point) => (acc._1 + point._1, acc._2 + point._2, acc._3 + point._3),
        (a, b) => (a._1 + b._1, a._2 + b._2, a._3 + b._3)
      )

    sc.stop()
    if (aggregate._3 == 0) println("Nema podataka.")
    else println(f"(${aggregate._1 / aggregate._3}%.6f, ${aggregate._2 / aggregate._3}%.6f)")
  }
}
