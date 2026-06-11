import java.io.File
import scala.io.Source
import scala.util.Try

object Main {
  private def readLines(path: String): List[String] = {
    val source = Source.fromFile(new File(path))
    try source.getLines().toList finally source.close()
  }

  private def stdinLines(): List[String] =
    Source.stdin.getLines().map(_.trim).filter(_.nonEmpty).toList

  private def isFiveDigitNumber(token: String): Boolean =
    Try(BigInt(token)).toOption.exists(n => n >= 10000 && n <= 99999)

  private def countFiveDigitNumbers(path: String): Int =
    readLines(path)
      .flatMap(_.trim.split("\\s+").filter(_.nonEmpty))
      .count(isFiveDigitNumber)

  def main(args: Array[String]): Unit = {
    val files = stdinLines()
    val results = Array.fill(files.length)(0)

    // Svaka nit obradjuje jednu datoteku i rezultat ostavlja na njenom indeksu.
    val threads = files.zipWithIndex.map { case (file, index) =>
      new Thread(new Runnable {
        override def run(): Unit = {
          results(index) = countFiveDigitNumbers(file)
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    files.indices.foreach { index =>
      println(s"${files(index)}: ${results(index)}")
    }
  }
}
