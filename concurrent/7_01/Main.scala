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

  def main(args: Array[String]): Unit = {
    val colorings = nonEmptyLines("ivans_coloring.data").map { line =>
      line
        .filter(c => c.isDigit || c == ',')
        .split(",")
        .filter(_.nonEmpty)
        .map(_.toInt)
    }

    def valid(c: Array[Int]): Boolean =
      c.length >= 7 &&
        c(0) != c(1) &&
        c(0) != c(3) &&
        c(1) != c(2) &&
        c(1) != c(3) &&
        c(2) != c(3) &&
        c(2) != c(4) &&
        c(3) != c(4) &&
        c(3) != c(5) &&
        c(4) != c(5) &&
        c(6) == 0 &&
        c(2) != 1

    println(colorings.count(valid))
  }
}
