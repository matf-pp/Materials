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
    val rows = nonEmptyLines("tiketi_podrske.csv").map(csv)
    val order = Seq("VISOK", "SREDNJI", "NIZAK")
    println(s"Obradjeno tiketa: ${rows.length}")
    println(s"Ukupno procenjeno vreme: ${rows.map(_(3).toInt).sum}")
    println("Po prioritetima: " + order.map(p => s"$p=${rows.count(_(1) == p)}").mkString(", "))
  }
}
