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
    val matrixLines = nonEmptyLines("matrica.txt")
    val dims = matrixLines.head.split("\\s+").map(_.toInt); val rows = dims(0); val cols = dims(1)
    val m = matrixLines.tail.take(rows).map(_.split("\\s+").map(_.toInt).take(cols))
    val v = nonEmptyLines("vektor.txt").head.split("\\s+").map(_.toInt)
    val res = m.map(row => (row zip v).map { case (x, y) => x * y }.sum)
    println("Rezultat: " + res.mkString(" "))
  }
}
