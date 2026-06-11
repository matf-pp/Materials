import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val n = stdinTokens()(0).toInt
    val monitor = new Object
    var open = false
    // Monitor cuva uslov open; pacijenti cekaju dok ordinacija ne postane dostupna.
    val threads = (1 to n).map { i =>
      new Thread(new Runnable { def run(): Unit = monitor.synchronized {
        while (!open) monitor.wait()
        println(s"Pacijent $i prozvan")
      }})
    }
    threads.foreach(_.start())
    Thread.sleep(20)
    monitor.synchronized {
      open = true
      println("Ordinacija otvorena")
      monitor.notifyAll()
    }
    threads.foreach(_.join())
  }
}
