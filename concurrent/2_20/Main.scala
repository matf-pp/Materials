import scala.collection.mutable
import scala.io.Source

case class Room(number: String, tip: Long)

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  private def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  private def readRooms(): Vector[Room] = {
    val source = Source.fromFile("sobe.txt")
    try {
      source.getLines().filter(_.trim.nonEmpty).map { line =>
        val parts = csv(line)
        Room(parts(0), parts(1).toLong)
      }.toVector
    } finally source.close()
  }

  def main(args: Array[String]): Unit = {
    val cleanerCount = stdinTokens().headOption.map(_.toInt).getOrElse(1) max 1
    val lock = new Object
    val queue = mutable.Queue[Room](readRooms(): _*)
    var cleaned = 0
    var tipBox = 0L

    // Monitor stiti red soba i zajednicke zbirne brojeve.
    val cleaners = (0 until cleanerCount).map { _ =>
      new Thread(new Runnable {
        override def run(): Unit = {
          var active = true
          while (active) {
            lock.synchronized {
              if (queue.isEmpty) {
                active = false
              } else {
                val room = queue.dequeue()
                cleaned += 1
                tipBox += room.tip
              }
            }
          }
        }
      })
    }

    cleaners.foreach(_.start())
    cleaners.foreach(_.join())

    println(s"Sredjeno soba: $cleaned")
    println(s"Ukupno baksisa: $tipBox")
    println(s"Baksis po cistacici: ${tipBox / cleanerCount}")
  }
}
