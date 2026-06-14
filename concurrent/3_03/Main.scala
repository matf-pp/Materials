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

  def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val matrixLines = nonEmptyLines("matrica.txt")
    val dims = matrixLines.head.split("\\s+").map(_.toInt)
    val rows = dims(0)
    val cols = dims(1)
    val m = matrixLines.tail.take(rows).map(_.split("\\s+").map(_.toInt).take(cols))
    val v = nonEmptyLines("vektor.txt").head.split("\\s+").map(_.toInt)
    val res = new Array[Int](rows)
    // Vrste matrice su nezavisne i mogu se podeliti nitima.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * rows / workers
          val until = (index + 1) * rows / workers
          for (i <- from until until) {
            res(i) = (m(i) zip v).map { case (x, y) => x * y }.sum
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println("Rezultat: " + res.mkString(" "))
  }
}
