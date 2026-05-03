import java.util.concurrent.Semaphore

object ParkingGaraza {

  def main(args: Array[String]): Unit = {
    val input = scala.io.StdIn.readLine().split(" ").map(_.toInt)
    val brojMesta = input(0)
    val brojAutomobila = input(1)

    val semafor = new Semaphore(brojMesta)

    val niti = (1 to brojAutomobila).map { id =>
      new Thread(() => {
        if (!semafor.tryAcquire()) {
          println(s"Auto $id ceka...")
          semafor.acquire()
        }

        println(s"Auto $id usao u garazu")

        // simulacija parkiranja
        Thread.sleep(100)

        println(s"Auto $id izasao iz garaze")
        semafor.release()
      })
    }

    niti.foreach(_.start())
    niti.foreach(_.join())

    println("Svi automobili su zavrsili")
  }
}
