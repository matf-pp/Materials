import java.io.File
import scala.collection.mutable
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
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val maxCost = stdinTokens()(1).toInt
    val spent = mutable.Map[String, Int]().withDefaultValue(0)
    val acceptedBy = mutable.TreeMap[String, Int]().withDefaultValue(0)
    var okc = 0
    var bad = 0

    nonEmptyLines("api_zahtevi.csv").foreach { line =>
      val row = csv(line)
      val user = row(1)
      val cost = row(2).toInt
      if (spent(user) + cost <= maxCost) {
        spent(user) += cost
        acceptedBy(user) += 1
        okc += 1
      } else {
        bad += 1
      }
    }
    println(s"Prihvaceno zahteva: $okc")
    println(s"Odbijeno zahteva: $bad")
    println("Prihvaceno po korisnicima: " + fmtPairs(acceptedBy))
  }
}
