import java.io.File
import scala.io.Source
import scala.util.Try

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
    def risk(r: Array[String]): Int = {
      val amount = if (r(1).toInt > 100000) 45 else 0
      val country = if (r(2) != "RS") 30 else 0
      amount + country + r(3).toInt * 10 + r(4).toInt * 5
    }
    val rows = nonEmptyLines("transakcije.csv").map(csv)
    val scored = rows.map(r => r(0) -> risk(r))
    val best = scored.sortBy { case (id, score) => (-score, -Try(id.drop(1).toInt).getOrElse(0)) }.head
    println(s"Ukupno transakcija: ${rows.length}")
    println(s"Za proveru: ${scored.count(_._2 >= 50)}")
    println(s"Najveci rizik: ${best._1}=${best._2}")
  }
}
