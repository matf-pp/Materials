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
    val deadline = stdinTokens()(1).toInt
    val rows = nonEmptyLines("izvestaji_sla.csv").map(csv)
    val done = rows.filter(_(1).toInt <= deadline)
    println(s"Zavrseno izvestaja: ${done.length}")
    println(s"Istekao rok: ${rows.length - done.length}")
    println(s"Zbir vrednosti: ${done.map(_(2).toInt).sum}")
  }
}
