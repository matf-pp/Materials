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

  def main(args: Array[String]): Unit = {
    val sums = mutable.TreeMap[String, (Double, Int)]()
    nonEmptyLines("merenja_senzora.csv").foreach { line =>
      val row = csv(line)
      val old = sums.getOrElse(row(0), (0.0, 0))
      sums(row(0)) = (old._1 + row(1).toDouble, old._2 + 1)
    }
    println("Proseci: " + sums.map { case (s, (sum, n)) => s"$s=${sum / n}" }.mkString(", "))
  }
}
