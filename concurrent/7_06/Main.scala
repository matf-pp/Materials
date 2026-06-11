import java.io.File
import scala.io.Source

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
    val offers = agencies.flatMap { case (name, file) =>
      nonEmptyLines(file)
        .map(_.split("\\s+"))
        .filter(row => row.length >= 3 && row(0) == dest)
        .map(row => (name, row(0), row(1).toInt, row(2).toInt))
    }
    if (offers.isEmpty) println(s"Nema ponuda za destinaciju $dest") else {
      println(s"Pristigle ponude za destinaciju $dest:")
      offers.foreach { case (a, _, days, price) => println(s"$a: $days dana za $price RSD") }
      val best = offers.minBy { case (a, _, days, price) => (price.toDouble / days, price, a) }
      println(s"Najpovoljnija ponuda: ${best._1} - ${best._3} dana za ${best._4} RSD")
    }
  }
}
