import java.io.File
import java.util.concurrent.ConcurrentSkipListSet
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

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("rezervacije_sedista.csv").map(csv)
    val reserved = new ConcurrentSkipListSet[String]()
    val locks = rows.map(row => row(2)).distinct.map(_ -> new Object).toMap
    val okCount = new AtomicInteger(0)
    val badCount = new AtomicInteger(0)
    // Svako sediste ima svoj monitor, pa nezavisna sedista ne blokiraju jedno drugo.
    val threads = rows.map { row =>
      new Thread(new Runnable {
        def run(): Unit = {
          val seat = row(2)
          locks(seat).synchronized {
            if (reserved.contains(seat)) badCount.incrementAndGet()
            else {
              reserved.add(seat)
              okCount.incrementAndGet()
            }
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Prihvaceno rezervacija: ${okCount.get}")
    println(s"Odbijeno rezervacija: ${badCount.get}")
    val orderedSeats = rows.map(_(2)).distinct.sorted.filter(reserved.contains)
    println("Zauzeta sedista: " + orderedSeats.mkString(", "))
  }
}
