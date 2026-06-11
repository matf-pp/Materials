import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val a = stdinTokens().map(_.toInt)
    val n = a(0)
    val inc = a(1)
    var counter = 0
    val lock = new Object
    // Monitor stiti brojac jer ga sve niti menjaju.
    val threads = (1 to n).map { _ =>
      new Thread(new Runnable {
        def run(): Unit =
          for (_ <- 0 until inc) {
            lock.synchronized {
              counter += 1
            }
          }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Brojac: $counter")
  }
}
