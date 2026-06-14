// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def main(args: Array[String]): Unit = {
    // Trazimo samo redove koji su greske i u kojima se pominje Spark.
    val conf = new SparkConf()
      .setAppName("SparkGreske")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val errors = sc.textFile("log.txt")
      // toLowerCase omogucava da pronadjemo "Spark", "spark" ili slicne varijante.
      .filter(line => line.startsWith("[error]") && line.toLowerCase.contains("spark"))
      .collect()

    sc.stop()
    if (errors.nonEmpty) println(errors.mkString("\n"))
  }
}
