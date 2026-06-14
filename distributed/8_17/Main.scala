// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    // Korisnik unosi marku, npr. Lenovo; poredjenje radimo bez razlike u velicini slova.
    val brand = StdIn.readLine().trim
    val conf = new SparkConf()
      .setAppName("NasumicneTransakcije")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    // Prva rec u liniji je marka uredjaja, ostatak linije je opis transakcije.
    val transactions = sc.textFile("uredjaji.txt")
      .filter { line =>
        val first = line.trim.split("\\s+", 2).headOption.getOrElse("")
        first.equalsIgnoreCase(brand)
      }
      // takeSample vraca najvise pet nasumicnih transakcija na driver.
      .takeSample(withReplacement = false, num = 5, seed = System.currentTimeMillis())

    sc.stop()
    if (transactions.nonEmpty) println(transactions.mkString("\n"))
  }
}
