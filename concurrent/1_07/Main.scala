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
    val files = nonEmptyLines("datoteke.txt")
    val totals = files.map { f =>
      val ls = readLines(f)
      (1, ls.length, ls.map(_.length).sum)
    }
    println(s"Ukupno datoteka: ${totals.map(_._1).sum}")
    println(s"Ukupno linija: ${totals.map(_._2).sum}")
    println(s"Ukupno znakova: ${totals.map(_._3).sum}")
  }
}
