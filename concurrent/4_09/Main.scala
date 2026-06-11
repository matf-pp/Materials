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

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val in = stdinTokens()
    val item = in(1)
    val maxLat = in(2).toInt
    val offers = nonEmptyLines("ponude_dobavljaca.csv")
      .map(csv)
      .filter(row => row(1) == item && row(4) == "OK" && row(3).toInt <= maxLat)
    val best = offers.sortBy(r => (r(2).toInt, r(3).toInt, r(0))).head
    println(s"Prihvatljivih ponuda: ${offers.length}")
    println(s"Najbolja ponuda: ${best(0)} ${best(2)}")
  }
}
