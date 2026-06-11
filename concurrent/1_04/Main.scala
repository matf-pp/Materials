import java.util.concurrent.atomic.AtomicInteger
import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val t = stdinTokens().map(_.toInt)
    val total = t(0)
    val threshold = t(1)
    // AtomicInteger omogucava da radna i glavna nit vide isti broj obradjenih poslova.
    val processed = new AtomicInteger(0)
    val worker = new Thread(new Runnable { def run(): Unit = {
      var stop = false
      while (!stop && processed.get < total) {
        val now = processed.incrementAndGet()
        if (Thread.currentThread.isInterrupted || now >= threshold) stop = true
      }
    }})
    worker.start()
    while (processed.get < threshold && processed.get < total) Thread.`yield`()
    if (processed.get >= threshold && processed.get < total) worker.interrupt()
    worker.join()
    println(s"Obradjeno poslova: ${processed.get}")
    println("Status: " + (if (processed.get < total) "PREKINUTO" else "ZAVRSENO"))
  }
}
