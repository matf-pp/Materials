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
    val counts = mutable.TreeMap[String, (Int, Int)]()
    nonEmptyLines("statusi_servisa.csv").foreach { line =>
      val row = csv(line)
      val old = counts.getOrElse(row(0), (0, 0))
      val error = if (row(1).toInt >= 500) 1 else 0
      counts(row(0)) = (old._1 + 1, old._2 + error)
    }
    val rates = counts.map { case (service, (total, err)) =>
      f"$service=${err * 100.0 / total}%.1f%%"
    }
    println("Stope gresaka: " + rates.mkString(", "))
  }
}
