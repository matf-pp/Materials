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
    val in = stdinTokens().map(_.toInt)
    val window = in(1)
    val duration = in(2)
    val windows = Math.ceil(duration.toDouble / window).toInt
    val sums = Array.fill(windows)(0)
    nonEmptyLines("metrike.csv").foreach { line =>
      val row = csv(line)
      val idx = row(0).toInt / window
      if (idx >= 0 && idx < windows) sums(idx) += row(2).toInt
    }
    println(s"Prozora: $windows")
    println(s"Ukupna vrednost: ${sums.sum}")
    println("Po prozorima: " + sums.zipWithIndex.map { case (v, i) => s"$i=$v" }.mkString(", "))
  }
}
