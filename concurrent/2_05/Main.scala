import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicInteger
import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val n = stdinTokens()(0).toInt
    val sem = new Semaphore(1)
    val sent = new AtomicInteger(0)
    // Semafor modeluje jedan izlazni kanal za slanje dokumenata.
    val threads = (1 to n).map { i =>
      new Thread(new Runnable { def run(): Unit = {
        sem.acquire()
        try {
          println(s"Dokument $i poslat")
          sent.incrementAndGet()
        } finally sem.release()
      }})
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Ukupno poslato: ${sent.get}")
  }
}
