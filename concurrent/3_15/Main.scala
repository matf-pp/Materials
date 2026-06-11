import scala.io.Source

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val tokens = stdinTokens()
    val n = tokens(0).toInt
    val vector = tokens.slice(1, 1 + n).map(_.toLong)
    val scalar = tokens(1 + n).toLong
    val threadCount = tokens(2 + n).toInt max 1

    // Svaka nit menja samo svoj deo vektora, pa nema potrebe za monitorom.
    val threads = (0 until threadCount).map { index =>
      new Thread(new Runnable {
        override def run(): Unit = {
          val from = index * n / threadCount
          val until = (index + 1) * n / threadCount
          for (i <- from until until) {
            vector(i) *= scalar
          }
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    println(s"Rezultat: ${vector.mkString(" ")}")
  }
}
