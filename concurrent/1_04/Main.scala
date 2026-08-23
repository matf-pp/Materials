import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}
import scala.util.Random

object Main {

  class Worker(
      val totalJobs: Int,
      val interrupted: AtomicBoolean,
      val processedJobs: AtomicInteger
  ) extends Thread {

    override def run(): Unit = {
      val random = new Random()

      while (processedJobs.get() < totalJobs && !interrupted.get()) {
        // Simulate processing the current job for 1–5 seconds.
        val seconds = random.nextInt(5) + 1
        Thread.sleep(seconds * 1000L)

        // The job has finished.
        processedJobs.incrementAndGet()
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val n = scala.io.StdIn.readInt()

    val interrupted = new AtomicBoolean(false)
    val processedJobs = new AtomicInteger(0)

    val worker = new Worker(n, interrupted, processedJobs)
    worker.start()

    // Wait for the cancellation signal.
    val signal = scala.io.StdIn.readLine()

    if (signal == "prekid") {
      interrupted.set(true)
    }

    // Wait for the worker to finish.
    worker.join()

    println(s"Obradjeno poslova: ${processedJobs.get()}")

    if (interrupted.get()) {
      println("Status: PREKINUTO")
    } else {
      println("Status: ZAVRSENO")
    }
  }
}

