import java.io.File
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
    val rows = nonEmptyLines("servisi.csv").map(csv)
    println(s"Ispravnih servisa: ${rows.count(r => r(1) == "OK")}")
    println(s"Sporih servisa: ${rows.count(r => r(2).toInt > 1000)}")
    println(rows.sortBy(_(0)).map(r => s"${r(0)}=${r(1)}").mkString(", "))
  }
}
