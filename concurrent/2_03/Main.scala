import java.util.concurrent.locks.ReentrantLock
import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val n = stdinTokens()(0).toInt
    val mutex = new ReentrantLock()
    // Brava predstavlja stampac koji u jednom trenutku koristi samo jedan radnik.
    val threads = (1 to n).map { i =>
      new Thread(new Runnable { def run(): Unit = {
        mutex.lock()
        try println(s"Radnik $i koristi stampac")
        finally if (mutex.isHeldByCurrentThread) mutex.unlock()
      }})
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println("Stampanje zavrseno")
  }
}
