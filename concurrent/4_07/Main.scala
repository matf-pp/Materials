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
    val okRows = nonEmptyLines("ogledala.csv").map(csv).filter(_(1) == "OK")
    val best = okRows.sortBy(r => (r(2).toInt, r(0))).head
    println(s"Uspesnih odgovora: ${okRows.length}")
    println(s"Izabrani odgovor: ${best(0)} ${best(2)}")
  }
}
