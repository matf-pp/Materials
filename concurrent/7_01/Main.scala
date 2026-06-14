import java.io.File
import java.util.concurrent.atomic.AtomicInteger
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

    val count = new AtomicInteger(0)
    // Svaka nit proverava svoj deo bojenja i uvecava zajednicki brojac.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * colorings.length / workers
          val until = (index + 1) * colorings.length / workers
          count.addAndGet(colorings.slice(from, until).count(valid))
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(count.get)
  }
}
