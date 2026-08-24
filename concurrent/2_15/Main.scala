import java.util.concurrent.CountDownLatch
import scala.util.Random

class PreparationThread(id: Int, latch: CountDownLatch) extends Thread {
  override def run(): Unit = {
    println(s"Priprema zadatka $id...")

    val seconds = Random.nextInt(3) + 1
    Thread.sleep(seconds * 1000L)

    println(s"Zadatak $id zavrsen.")
    latch.countDown()
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val n = scala.io.StdIn.readInt()

    val required = (n + 1) / 2
    val latch = new CountDownLatch(required)

    val threads = (1 to n).map { id =>
      new PreparationThread(id, latch)
    }

    threads.foreach(_.start())

    println("Priprema u toku...")

    // Ceka da najmanje polovina zadataka bude zavrsena.
    latch.await()

    println("Obrada narudzbina zapoceta.")

    // Ceka da se zavrse i preostali pripremni zadaci.
    threads.foreach(_.join())

    println("Obrada narudzbina zavrsena.")
  }
}

