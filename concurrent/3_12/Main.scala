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

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val t = stdinTokens().map(_.toInt)
    val workers = t(0).max(1)
    val threshold = t(1)
    val rows = nonEmptyLines("zalihe.csv").map(csv)
    val partial = Array.fill(workers)((0, 0))

    // Svaka nit vraca vrednost zaliha i broj niskih stanja za svoj blok.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * rows.length / workers
          val until = (index + 1) * rows.length / workers
          var value = 0
          var low = 0
          rows.slice(from, until).foreach { row =>
            val qty = row(1).toInt
            value += qty * row(2).toInt
            if (qty < threshold) low += 1
          }
          partial(index) = (value, low)
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val value = partial.map(_._1).sum
    val low = partial.map(_._2).sum
    println(s"Vrednost zaliha: $value")
    println(s"Nisko stanje: $low")
  }
}
