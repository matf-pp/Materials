import java.io.{File, PrintWriter}
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

  def writeLines(file: String, lines: Seq[String]): Unit = {
    val pw = new PrintWriter(new File(file))
    try lines.foreach(pw.println) finally pw.close()
  }

  def main(args: Array[String]): Unit = {
    def readMatrix(file: String): Array[Array[Int]] = {
      val lines = nonEmptyLines(file)
      val dims = lines.head.split("\\s+").map(_.toInt)
      lines.tail.take(dims(0)).map { line =>
        line.split("\\s+").map(_.toInt).take(dims(1))
      }.toArray
    }

    val a = readMatrix("matrica_a.txt")
    val b = readMatrix("matrica_b.txt")
    val res = Array.ofDim[Int](a.length, b(0).length)
    // Svaka celija rezultata je skalarni proizvod jedne vrste i jedne kolone.
    for (i <- a.indices; j <- b(0).indices) {
      res(i)(j) = (for (k <- b.indices) yield a(i)(k) * b(k)(j)).sum
    }
    writeLines("matrica_c.txt", res.map(_.mkString(" ")))
  }
}
