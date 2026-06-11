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
    val t = stdinTokens().map(_.toInt)
    val threshold = t(1)
    var value = 0
    var low = 0

    nonEmptyLines("zalihe.csv").foreach { line =>
      val row = csv(line)
      val qty = row(1).toInt
      value += qty * row(2).toInt
      if (qty < threshold) low += 1
    }
    println(s"Vrednost zaliha: $value")
    println(s"Nisko stanje: $low")
  }
}
