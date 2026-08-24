import java.util.concurrent.Semaphore
import scala.util.Random

class Query(val id: Int, val semaphore: Semaphore) extends Thread:
  override def run(): Unit =
    semaphore.acquire()

    try
      println(s"Upit $id se izvrsava")

      val executionTime = 5 + Random.nextInt(6)
      Thread.sleep(executionTime * 1000L)

      println(s"Upit $id zavrsen")
    finally
      semaphore.release()


@main def Main(): Unit =
  val input = scala.io.StdIn.readLine().split(" ").map(_.toInt)
  val maxConcurrent = input(0)
  val queryCount = input(1)

  val semaphore = new Semaphore(maxConcurrent)

  val threads = (1 to queryCount).map { id =>
    val query = new Query(id, semaphore)
    query.start()
    query
  }

  threads.foreach(_.join())

  println("Svi upiti su izvrseni")
