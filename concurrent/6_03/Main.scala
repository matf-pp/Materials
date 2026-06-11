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

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("poslovi_ws.csv").map(csv)
    val total = rows.map(_(1).toInt).sum; val best = rows.maxBy(_(1).toInt)
    println(s"Obradjeno poslova: ${rows.length}")
    println(s"Ukupna cena: $total")
    println(s"Najskuplji posao: ${best(0)}=${best(1)}")
  }
}
