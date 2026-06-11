import scala.io.Source

case class Participant(name: String, numbers: Set[Int])

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  private def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  private def readParticipants(): Vector[Participant] = {
    val source = Source.fromFile("ucesnici.txt")
    try {
      source.getLines().filter(_.trim.nonEmpty).map { line =>
        val parts = csv(line)
        Participant(parts(0), parts.drop(1).map(_.toInt).toSet)
      }.toVector
    } finally source.close()
  }

  def main(args: Array[String]): Unit = {
    val tokens = stdinTokens()
    val fund = tokens(0).toLong
    val drawnNumbers = tokens.drop(1).take(3).map(_.toInt).toVector
    val drawnSet = drawnNumbers.toSet
    val participants = readParticipants()
    val lock = new Object
    var published = false
    val payouts = Array.fill(participants.length)(0L)

    // Monitor drzi ucesnike dok izvuceni brojevi ne budu dostupni svima.
    val threads = participants.zipWithIndex.map { case (participant, index) =>
      new Thread(new Runnable {
        override def run(): Unit = {
          lock.synchronized {
            while (!published) lock.wait()
          }

          val hits = participant.numbers.count(drawnSet.contains)
          payouts(index) =
            if (hits == 3) fund
            else if (hits == 2) fund * 40 / 100
            else if (hits == 1) fund * 10 / 100
            else 0L
        }
      })
    }

    threads.foreach(_.start())
    lock.synchronized {
      published = true
      lock.notifyAll()
    }
    threads.foreach(_.join())

    println(s"Izvuceni brojevi: ${drawnNumbers.mkString(" ")}")
    participants.indices.foreach { index =>
      println(s"${participants(index).name}: ${payouts(index)}")
    }
    val total = payouts.sum
    println(s"Ukupna isplata: $total")
    println(s"Preostalo: ${fund - total}")
  }
}
