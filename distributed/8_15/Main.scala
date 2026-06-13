import java.io.PrintWriter
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  case class Temp(month: Int, day: Int, year: Int, value: Double)

  def parse(line: String): Option[Temp] = {
    val parts = line.trim.split("\\s+")
    if (parts.length != 4) None
    else Some(Temp(parts(0).toInt, parts(1).toInt, parts(2).toInt, parts(3).toDouble))
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("NajtoplijiDani")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val hottestDays = sc.textFile("temperatureBoston.txt")
      .flatMap(parse)
      .map(temp => (temp.year, temp))
      .reduceByKey { (a, b) =>
        if (a.value > b.value) a
        else if (b.value > a.value) b
        else if (a.month < b.month || (a.month == b.month && a.day <= b.day)) a
        else b
      }
      .sortByKey()
      .map { case (_, temp) => s"${temp.month} ${temp.day} ${temp.year}" }
      .collect()

    sc.stop()

    val writer = new PrintWriter("maxTemp.txt")
    try writer.println(hottestDays.mkString("\n"))
    finally writer.close()
  }
}
