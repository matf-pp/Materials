import java.util.concurrent.Semaphore
import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val a = stdinTokens().map(_.toInt)
    val places = a(0)
    val cars = a(1)
    val sem = new Semaphore(places)
    // Semafor cuva broj slobodnih mesta na parkingu.
    val threads = (1 to cars).map { i =>
      new Thread(new Runnable { def run(): Unit = {
        sem.acquire()
        try {
          println(s"Auto $i usao")
          println(s"Auto $i izasao")
        } finally sem.release()
      }})
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println("Svi automobili su zavrsili")
  }
}
