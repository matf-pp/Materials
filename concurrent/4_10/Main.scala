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
    val rows = nonEmptyLines("korisnici_onboarding.csv").map(csv)
    val ready = rows.count(r => r.drop(1).forall(_ == "OK"))
    println(s"Spremnih korisnika: $ready")
    println(s"Odbijenih korisnika: ${rows.length - ready}")
  }
}
