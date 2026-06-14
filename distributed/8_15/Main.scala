// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import java.io.PrintWriter
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Jedan red iz datoteke predstavljamo strukturisano, da kasniji kod bude citljiviji.
  case class Temp(month: Int, day: Int, year: Int, value: Double)

  def parse(line: String): Option[Temp] = {
    // Neispravne redove preskacemo vracanjem None.
    val parts = line.trim.split("\\s+")
    if (parts.length != 4) None
    else Some(Temp(parts(0).toInt, parts(1).toInt, parts(2).toInt, parts(3).toDouble))
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("NajtoplijiDani")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    // Kljuc je godina; vrednost je najtopliji dan koji trenutno znamo za tu godinu.
    val hottestDays = sc.textFile("temperatureBoston.txt")
      .flatMap(parse)
      .map(temp => (temp.year, temp))
      .reduceByKey { (a, b) =>
        // Ako su temperature jednake, zadrzavamo raniji datum.
        if (a.value > b.value) a
        else if (b.value > a.value) b
        else if (a.month < b.month || (a.month == b.month && a.day <= b.day)) a
        else b
      }
      .sortByKey()
      .map { case (_, temp) => s"${temp.month} ${temp.day} ${temp.year}" }
      .collect()

    sc.stop()

    // Rezultat zadatak trazi u obicnoj tekstualnoj datoteci maxTemp.txt.
    val writer = new PrintWriter("maxTemp.txt")
    try writer.println(hottestDays.mkString("\n"))
    finally writer.close()
  }
}
