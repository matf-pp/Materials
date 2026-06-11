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
    val in = stdinTokens()
    val limit = in(1).toInt
    val counts = mutable.TreeMap[String, Int]().withDefaultValue(0)
    var sent = 0
    var cancelled = 0

    nonEmptyLines("kanali_obavestenja.csv").foreach { line =>
      val row = csv(line)
      val choices = Seq(
        "EMAIL" -> row(1).toInt,
        "SMS" -> row(2).toInt,
        "PUSH" -> row(3).toInt
      ).filter(_._2 <= limit)

      if (choices.isEmpty) {
        cancelled += 1
      } else {
        val channel = choices.sortBy { case (name, ms) => (ms, name) }.head._1
        counts(channel) += 1
        sent += 1
      }
    }
    println(s"Poslato obavestenja: $sent")
    println(s"Otkazano obavestenja: $cancelled")
    println("Po kanalima: " + fmtPairs(counts))
  }
}
