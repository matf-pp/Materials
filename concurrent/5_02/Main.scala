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
    val requests = nonEmptyLines("zahtevi_kurseva.txt")
    val rates = nonEmptyLines("kursevi.csv").map(csv).map(r => r(0) -> r(1)).toMap
    val unique = requests.distinct
    println(s"Obradjeno zahteva: ${requests.length}")
    println(s"Jedinstvenih ucitavanja: ${unique.length}")
    println("Kursevi: " + unique.sorted.map(p => s"$p=${rates.getOrElse(p, "")}").mkString(", "))
  }
}
