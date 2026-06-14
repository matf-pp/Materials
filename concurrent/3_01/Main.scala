import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def main(args: Array[String]): Unit = {
    val lines = stdinLines()
    val n = lines(0).trim.toInt
    val values = lines(1).trim.split("\\s+").map(_.toInt).take(n)
    val workers = lines.lift(2).map(_.trim.toInt).getOrElse(1).max(1)
    val partial = new Array[Int](workers)
    // Svaka nit sabira svoj blok vektora, pa se parcijalne sume spajaju na kraju.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * values.length / workers
          val until = (index + 1) * values.length / workers
          partial(index) = values.slice(from, until).sum
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Suma: ${partial.sum}")
  }
}
