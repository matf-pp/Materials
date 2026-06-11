import java.io.File
import scala.collection.mutable
import scala.io.Source

object Main {
  def readLines(file: String): List[String] = {
    val f = new File(file)
    if (!f.exists()) Nil else {
      val src = Source.fromFile(f)
      try src.getLines().toList finally src.close()
    }
  }

  def nonEmptyLines(file: String): List[String] = readLines(file).map(_.trim).filter(_.nonEmpty)

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val counts = mutable.TreeMap[String, Int]().withDefaultValue(0); var total = 0
    nonEmptyLines("zahtevi_locale.csv").foreach { l => val loc = csv(l)(2); counts(loc) += 1; total += 1 }
    println(s"Obradjeno zahteva: $total")
    println("Po locale: " + fmtPairs(counts))
  }
}
