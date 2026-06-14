// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import java.io.File
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Spark nece prepisati postojeci izlazni direktorijum, pa ga pre upisa brisemo.
  def deleteRecursively(file: File): Unit = {
    if (file.exists()) {
      if (file.isDirectory) file.listFiles().foreach(deleteRecursively)
      file.delete()
    }
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ApacheHadoopPaketi")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    // wholeTextFiles ucitava celu datoteku kao jedan tekst, sto olaksava gledanje susednih redova.
    val packageSizes = sc.wholeTextFiles("mavenLog.txt")
      .flatMap { case (_, text) =>
        val lines = text.split("\\r?\\n").toSeq
        // Red "Downloaded:" prati red sa velicinom, zato posmatramo prozore od po dva reda.
        lines.sliding(2).collect {
          case Seq(downloaded, sizeLine)
              if downloaded.startsWith("Downloaded:") &&
                 downloaded.toLowerCase.contains("hadoop") =>
            val packageName = downloaded.trim.split("/").last.trim
            val size = sizeLine.trim
              .stripPrefix("(")
              .split("\\s+at\\s+")
              .headOption
              .getOrElse("")
              .trim
            s"$packageName: $size"
        }
      }

    deleteRecursively(new File("ApacheDownloaded"))
    // saveAsTextFile pravi direktorijum sa part-* datotekama, sto je standardni Spark izlaz.
    packageSizes.saveAsTextFile("ApacheDownloaded")
    sc.stop()
  }
}
