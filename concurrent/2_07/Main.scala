import java.io.File
import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}
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

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val input = stdinTokens().map(_.toInt)
    val consumers = input(0)
    val cap = input(1)
    val queue = new ArrayBlockingQueue[String](cap)
    val done = new AtomicBoolean(false)
    val count = new AtomicInteger(0)
    // Red razdvaja proizvodjaca poslova od potrosaca koji ih obradjuju.
    val producer = new Thread(new Runnable { def run(): Unit = {
      nonEmptyLines("poslovi.txt").foreach(queue.put)
      done.set(true)
    }})
    val workers = (1 to consumers).map { _ =>
      new Thread(new Runnable { def run(): Unit = {
        while (!done.get || !queue.isEmpty) {
          val job = queue.poll(20, TimeUnit.MILLISECONDS)
          if (job != null) count.incrementAndGet()
        }
      }})
    }
    workers.foreach(_.start())
    producer.start()
    producer.join()
    workers.foreach(_.join())
    println(s"Obradjeno poslova: ${count.get}")
  }
}
