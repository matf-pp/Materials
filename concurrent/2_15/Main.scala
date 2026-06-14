import java.io.File
import java.util.concurrent.CountDownLatch
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
    val rows = nonEmptyLines("servisi_start.csv").map(csv)
    val gate = new CountDownLatch(1)
    val started = new AtomicInteger(0)
    val skipped = new AtomicInteger(0)
    // Servisne niti cekaju zajednicku kapiju pre odluke o pokretanju.
    val threads = rows.map { row =>
      new Thread(new Runnable {
        def run(): Unit = {
          gate.await()
          if (row(1) == "DA") started.incrementAndGet()
          else skipped.incrementAndGet()
        }
      })
    }
    threads.foreach(_.start())
    gate.countDown()
    threads.foreach(_.join())
    println(s"Pokrenuto servisa: ${started.get}")
    println(s"Preskoceno servisa: ${skipped.get}")
  }
}
