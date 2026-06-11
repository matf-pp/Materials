import java.io.File
import java.util.concurrent.locks.ReentrantLock
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

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val n = stdinTokens()(0).toInt
    val values = nonEmptyLines("uplate.txt").map(_.toInt)
    val lock = new ReentrantLock()
    var total = 0
    val chunk = Math.ceil(values.length.toDouble / n).toInt.max(1)
    // Niti sabiraju svoje blokove, a brava stiti samo spajanje u ukupan zbir.
    val threads = (0 until n).map { i =>
      new Thread(new Runnable { def run(): Unit = {
        val from = i * chunk
        val until = Math.min((i + 1) * chunk, values.length)
        val local = values.slice(from, until).sum
        lock.lock()
        try total += local
        finally lock.unlock()
      }})
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Stanje racuna: $total")
  }
}
