import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def afterOctober2015(dateTime: String): Boolean = {
    val date = dateTime.split("\\s+").headOption.getOrElse("")
    val parts = date.split("/")
    parts.length == 3 && {
      val month = parts(0).toInt
      val year = parts(2).toInt
      year > 2015 || (year == 2015 && month > 10)
    }
  }

  def hourInRange(dateTime: String): Boolean = {
    val time = dateTime.split("\\s+").drop(1).headOption.getOrElse("")
    val parts = time.split(":")
    parts.nonEmpty && {
      val hour = parts(0).toInt
      hour >= 10 && hour <= 15
    }
  }

  def hasLink(text: String): Boolean = {
    val lower = text.toLowerCase
    lower.contains("http://") || lower.contains("https://") ||
      lower.contains("www.") || lower.contains("pic.twitter.com")
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("TrumpTviti")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val count = sc.textFile("trump.csv")
      .filter(!_.startsWith("ID,"))
      .map(columns)
      .filter(row =>
        row.length >= 3 &&
          afterOctober2015(row(1)) &&
          hourInRange(row(1)) &&
          !hasLink(row(2)))
      .count()

    sc.stop()
    println(count)
  }
}
