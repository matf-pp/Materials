import scala.collection.mutable
import scala.io.Source

case class Bike(id: String, payment: Long)

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  private def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  private def readBikes(): Vector[Bike] = {
    val source = Source.fromFile("bicikli.txt")
    try {
      source.getLines().filter(_.trim.nonEmpty).map { line =>
        val parts = csv(line)
        Bike(parts(0), parts(1).toLong)
      }.toVector
    } finally source.close()
  }

  def main(args: Array[String]): Unit = {
    val repairerCount = stdinTokens().headOption.map(_.toInt).getOrElse(1) max 1
    val lock = new Object
    val queue = mutable.Queue[Bike](readBikes(): _*)
    var repaired = 0
    var cashbox = 0L

    // Monitor stiti red bicikala i zajednicku kasu servisa.
    val repairers = (0 until repairerCount).map { _ =>
      new Thread(new Runnable {
        override def run(): Unit = {
          var active = true
          while (active) {
            lock.synchronized {
              if (queue.isEmpty) {
                active = false
              } else {
                val bike = queue.dequeue()
                repaired += 1
                cashbox += bike.payment
              }
            }
          }
        }
      })
    }

    repairers.foreach(_.start())
    repairers.foreach(_.join())

    println(s"Popravljeno bicikala: $repaired")
    println(s"Ukupno uplata: $cashbox")
    println(s"Plata po serviseru: ${cashbox / repairerCount}")
  }
}
