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
    val in = stdinTokens(); val word = in(1)
    val hits = nonEmptyLines("pretraga.csv").map(csv).filter(r => r(1).contains(word))
    val best = hits.sortBy(r => (-r(2).toInt, r(1))).head
    println(s"Pogodaka: ${hits.length}")
    println(s"Najbolja stavka: ${best(1)}=${best(2)}")
  }
}
