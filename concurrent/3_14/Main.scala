import scala.io.Source

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val tokens = stdinTokens()
    val n = tokens(0).toInt
    val first = tokens.slice(1, 1 + n).map(_.toLong)
    val second = tokens.slice(1 + n, 1 + 2 * n).map(_.toLong)
    val threadCount = tokens(1 + 2 * n).toInt max 1

    // Niti rade nad nepovezanim blokovima vektora, bez deljenog pisanja.
    val threads = (0 until threadCount).map { index =>
      new Thread(new Runnable {
        override def run(): Unit = {
          val from = index * n / threadCount
          val until = (index + 1) * n / threadCount
          for (i <- from until until) {
            first(i) += second(i)
          }
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    println(s"Rezultat: ${first.mkString(" ")}")
  }
}
