// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Delimo red po zarezima i uklanjamo razmake; ulaz zadatka ne trazi punu CSV podrsku.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def afterOctober2015(dateTime: String): Boolean = {
    // Datum je zapisan u obliku mesec/dan/godina pre vremena.
    val date = dateTime.split("\\s+").headOption.getOrElse("")
    val parts = date.split("/")
    parts.length == 3 && {
      val month = parts(0).toInt
      val year = parts(2).toInt
      year > 2015 || (year == 2015 && month > 10)
    }
  }

  def hourInRange(dateTime: String): Boolean = {
    // Sat je prvi broj posle datuma.
    val time = dateTime.split("\\s+").drop(1).headOption.getOrElse("")
    val parts = time.split(":")
    parts.nonEmpty && {
      val hour = parts(0).toInt
      hour >= 10 && hour <= 15
    }
  }

  def hasLink(text: String): Boolean = {
    // Nekoliko cestih oznaka za URL dovoljno je za ovaj primer.
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
      // Brojimo tvitove posle oktobra 2015, izmedju 10 i 15h, bez linkova.
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
