import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val n = stdinTokens()(0).toInt
    val result = new Array[Int](n + 1)
    // Svaka nit racuna jednu vrednost i upisuje je na odvojenu poziciju niza.
    val threads = (1 to n).map { i =>
      new Thread(new Runnable {
        def run(): Unit = result(i) = i * i
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println((1 to n).map(i => s"$i=${result(i)}").mkString(", "))
  }
}
