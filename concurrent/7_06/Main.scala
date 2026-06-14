import java.io.File
import scala.collection.mutable
import scala.io.Source

case class Offer(agency: String, dest: String, days: Int, price: Int, agencyIndex: Int, offerIndex: Int)

object Main {
  def readLines(file: String): List[String] = {
    val f = new File(file)
    if (!f.exists()) Nil else {
      val src = Source.fromFile(f)
      try src.getLines().toList finally src.close()
    }
  }

  def nonEmptyLines(file: String): List[String] = readLines(file).map(_.trim).filter(_.nonEmpty)

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val dest = stdinTokens().headOption.getOrElse("")
    val agencies = Seq(
      "ITTravel" -> "ITTravel.data",
      "LignjaTravel" -> "LignjaTravel.data",
      "Travellove" -> "Travellove.data"
    )
    val queue = mutable.Queue[Offer]()
    val lock = new Object
    // Svaka agencija cita svoj fajl u posebnoj niti i dodaje ponude u deljeni red.
    val threads = agencies.zipWithIndex.map { case ((name, file), agencyIndex) =>
      new Thread(new Runnable {
        def run(): Unit = {
          nonEmptyLines(file).map(_.split("\\s+")).zipWithIndex.foreach { case (row, offerIndex) =>
            if (row.length >= 3 && row(0) == dest) {
              val offer = Offer(name, row(0), row(1).toInt, row(2).toInt, agencyIndex, offerIndex)
              lock.synchronized {
                queue.enqueue(offer)
              }
            }
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val offers = queue.toVector.sortBy(offer => (offer.agencyIndex, offer.offerIndex))
    if (offers.isEmpty) println(s"Nema ponuda za destinaciju $dest") else {
      println(s"Pristigle ponude za destinaciju $dest:")
      offers.foreach { offer => println(s"${offer.agency}: ${offer.days} dana za ${offer.price} RSD") }
      val best = offers.minBy(offer => (offer.price.toDouble / offer.days, offer.price, offer.agency))
      println(s"Najpovoljnija ponuda: ${best.agency} - ${best.days} dana za ${best.price} RSD")
    }
  }
}
