import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def main(args: Array[String]): Unit = {
    val lines = stdinLines()
    val n = lines(0).trim.toInt
    val a = lines(1).trim.split("\\s+").map(_.toInt).take(n)
    val b = lines(2).trim.split("\\s+").map(_.toInt).take(n)
    val workers = lines.lift(3).map(_.trim.toInt).getOrElse(1).max(1)
    val partial = new Array[Int](workers)
    // Svaka nit racuna lokalni deo skalarnog proizvoda.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * n / workers
          val until = (index + 1) * n / workers
          partial(index) = (from until until).map(i => a(i) * b(i)).sum
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Skalarni proizvod: ${partial.sum}")
  }
}
