import scala.io.Source

case class Match(odds: Map[String, Double], outcome: String)
case class Bettor(name: String, stake: Double, picks: Vector[(String, String)])

object Main {
  private def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  private def readMatches(): Map[String, Match] = {
    val source = Source.fromFile("utakmice.txt")
    try {
      source.getLines().filter(_.trim.nonEmpty).map { line =>
        val parts = csv(line)
        parts(0) -> Match(
          Map("1" -> parts(1).toDouble, "x" -> parts(2).toDouble, "2" -> parts(3).toDouble),
          parts(4)
        )
      }.toMap
    } finally source.close()
  }

  private def readBettors(): Vector[Bettor] = {
    val source = Source.fromFile("kladionicari.txt")
    try {
      source.getLines().filter(_.trim.nonEmpty).map { line =>
        val parts = csv(line)
        val picks =
          if (parts.length < 3 || parts(2).isEmpty) Vector.empty
          else parts(2).split(";").toVector.map { pick =>
            val p = pick.split(":", -1).map(_.trim)
            p(0) -> p(1)
          }
        Bettor(parts(0), parts(1).toDouble, picks)
      }.toVector
    } finally source.close()
  }

  private def money(value: Double): String = f"$value%.2f"

  def main(args: Array[String]): Unit = {
    val matches = readMatches()
    val bettors = readBettors()
    val lock = new Object
    var published = false
    val results = Array.fill(bettors.length)("")
    val payouts = Array.fill(bettors.length)(0.0)

    // Monitor drzi igrace dok rezultati utakmica ne budu objavljeni.
    val threads = bettors.zipWithIndex.map { case (bettor, index) =>
      new Thread(new Runnable {
        override def run(): Unit = {
          lock.synchronized {
            while (!published) lock.wait()
          }

          var hits = 0
          var payout = 0.0
          bettor.picks.foreach { case (matchName, pick) =>
            matches.get(matchName).foreach { m =>
              if (m.outcome == pick) {
                hits += 1
                payout += bettor.stake * m.odds(pick)
              }
            }
          }
          payouts(index) = payout
          results(index) = s"${bettor.name}: pogodjeno $hits, isplata ${money(payout)}"
        }
      })
    }

    threads.foreach(_.start())
    lock.synchronized {
      published = true
      lock.notifyAll()
    }
    threads.foreach(_.join())

    results.foreach(println)
    println(s"Ukupna isplata: ${money(payouts.sum)}")
  }
}
