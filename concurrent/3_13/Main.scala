import java.io.File
import scala.collection.mutable
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

  def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val rows = nonEmptyLines("statusi_servisa.csv").map(csv)
    val local = Array.tabulate(workers)(_ => mutable.Map[String, (Int, Int)]())
    // Svaka nit lokalno broji ukupan broj odgovora i serverske greske.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * rows.length / workers
          val until = (index + 1) * rows.length / workers
          rows.slice(from, until).foreach { row =>
            val old = local(index).getOrElse(row(0), (0, 0))
            val error = if (row(1).toInt >= 500) 1 else 0
            local(index)(row(0)) = (old._1 + 1, old._2 + error)
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val counts = mutable.TreeMap[String, (Int, Int)]()
    local.foreach { part =>
      part.foreach { case (service, (total, err)) =>
        val old = counts.getOrElse(service, (0, 0))
        counts(service) = (old._1 + total, old._2 + err)
      }
    }
    val rates = counts.map { case (service, (total, err)) =>
      f"$service=${err * 100.0 / total}%.1f%%"
    }
    println("Stope gresaka: " + rates.mkString(", "))
  }
}
