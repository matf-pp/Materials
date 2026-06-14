// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD._

object Main {

   def main(args: Array[String]){

      val konf = new SparkConf()
        .setAppName("SkalarniProizvod")
        .setMaster("local[4]")

      val sk = new SparkContext(konf)

      // Otvaramo datoteku i ucitavamo prvi vektor.
      val vek1RDD = sk.textFile("vektor1.txt")
                      // Razdvajamo elemente vektora iz niske koristeci separator ", ".
                      .flatMap(_.split(", "))
                      // Kastujemo niske u tip Integer.
                      .map(_.toInt)

      val vek2RDD = sk.textFile("vektor2.txt")
                      // Drugi vektor obradjujemo na isti nacin kao prvi.
                      .flatMap(_.split(", "))
                      .map(_.toInt)

      // Spajamo vektore funkcijom zip tako da dobijemo parove
      // (a1,b1), (a2,b2), ... (an,bn).
      val skProizvod = vek1RDD.zip(vek2RDD)
                              // zip spaja elemente na istim pozicijama.
                              // Zadatak pretpostavlja da su vektori iste duzine.
                              // Mnozimo elemente para.
                              .map(par => par._1 * par._2)
                              // Pomnozene elemente parova sabiramo.
                              .reduce((a, b) => a+b)
      sk.stop()

      println("Skalarni proizvod je: ")
      println(skProizvod)
  }
}
