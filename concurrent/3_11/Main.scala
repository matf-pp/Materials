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
    val rows = nonEmptyLines("merenja_senzora.csv").map(csv)
    val local = Array.tabulate(workers)(_ => mutable.Map[String, (Double, Int)]())
    // Niti prave lokalne zbirne parove, a proseci se racunaju posle spajanja.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * rows.length / workers
          val until = (index + 1) * rows.length / workers
          rows.slice(from, until).foreach { row =>
            val old = local(index).getOrElse(row(0), (0.0, 0))
            local(index)(row(0)) = (old._1 + row(1).toDouble, old._2 + 1)
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val sums = mutable.TreeMap[String, (Double, Int)]()
    local.foreach { part =>
      part.foreach { case (sensor, (sum, n)) =>
        val old = sums.getOrElse(sensor, (0.0, 0))
        sums(sensor) = (old._1 + sum, old._2 + n)
      }
    }
    println("Proseci: " + sums.map { case (s, (sum, n)) => s"$s=${sum / n}" }.mkString(", "))
  }
}
