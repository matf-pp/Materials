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
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val rows = nonEmptyLines("prodaja.csv").map(csv)
    val local = Array.tabulate(workers)(_ => mutable.Map[String, Int]().withDefaultValue(0))
    // Lokalni zbirovi po regionima izbegavaju deljeni upis tokom obrade.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * rows.length / workers
          val until = (index + 1) * rows.length / workers
          rows.slice(from, until).foreach(row => local(index)(row(0)) += row(2).toInt)
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val sums = mutable.TreeMap[String, Int]().withDefaultValue(0)
    local.foreach(_.foreach { case (region, sum) => sums(region) += sum })
    println(s"Ukupna prodaja: ${sums.values.sum}")
    println("Po regionima: " + fmtPairs(sums))
  }
}
