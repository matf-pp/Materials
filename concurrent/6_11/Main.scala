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
    val rows = nonEmptyLines("porudzbine_future.csv").map(csv)
    val before = rows.map(_(2).toInt).sum
    val after = rows.map(r => if (r(3).toInt >= 1000) (r(2).toInt * 0.9).toInt else r(2).toInt).sum
    println(s"Ukupno pre popusta: $before")
    println(s"Ukupno posle popusta: $after")
  }
}
