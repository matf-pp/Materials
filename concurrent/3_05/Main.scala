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

  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val lines = readLines("tekst.txt")
    val local = Array.tabulate(workers)(_ => mutable.Map[String, Int]().withDefaultValue(0))
    // Niti broje reci lokalno; deljena mapa se pravi tek posle njihovog zavrsetka.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * lines.length / workers
          val until = (index + 1) * lines.length / workers
          lines.slice(from, until).foreach { line =>
            val words = line.toLowerCase
              .replaceAll("[^a-z0-9]+", " ")
              .split("\\s+")
              .filter(_.nonEmpty)

            words.foreach(word => local(index)(word) += 1)
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val counts = mutable.TreeMap[String, Int]().withDefaultValue(0)
    local.foreach(_.foreach { case (word, count) => counts(word) += count })
    println(fmtPairs(counts))
  }
}
