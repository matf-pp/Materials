import scala.collection.mutable
import scala.io.Source

case class Client(name: String, loan: Long, index: Int)

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  private def readClients(): Vector[Client] = {
    val source = Source.fromFile("red_klijenata.txt")
    try {
      source.getLines().filter(_.trim.nonEmpty).zipWithIndex.map { case (line, index) =>
        val parts = line.trim.split("\\s+")
        Client(parts(0), parts(1).toLong, index)
      }.toVector
    } finally source.close()
  }

  private def money(value: BigDecimal): String =
    f"${value.setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble}%.2f"

  def main(args: Array[String]): Unit = {
    val tokens = stdinTokens()
    val initialCapital = tokens(0).toLong
    val interestRate = BigDecimal(tokens(1))
    val workerCount = tokens(2).toInt max 1
    val clients = readClients()

    val lock = new Object
    val queue = mutable.Queue[Client](clients: _*)
    val results = Array.fill(clients.length)("")
    var capital = initialCapital
    var totalDebt = BigDecimal(0)

    // Monitor stiti red klijenata, kapital i ukupan dug banke.
    val workers = (0 until workerCount).map { _ =>
      new Thread(new Runnable {
        override def run(): Unit = {
          var active = true
          while (active) {
            lock.synchronized {
              if (queue.isEmpty) {
                active = false
              } else {
                val client = queue.dequeue()
                if (client.loan <= capital) {
                  capital -= client.loan
                  val debt = BigDecimal(client.loan) * (BigDecimal(100) + interestRate) / BigDecimal(100)
                  totalDebt += debt
                  results(client.index) = s"Odobreno: ${client.name} ${client.loan} dug ${money(debt)}"
                } else {
                  results(client.index) = s"Odbijeno: ${client.name} ${client.loan}"
                }
              }
            }
          }
        }
      })
    }

    workers.foreach(_.start())
    workers.foreach(_.join())

    results.foreach(println)
    println(s"Kapital: $capital")
    println(s"Ukupan dug: ${money(totalDebt)}")
    println(s"Zarada banke: ${money(totalDebt - BigDecimal(initialCapital - capital))}")
  }
}
