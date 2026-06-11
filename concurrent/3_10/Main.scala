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
    var valid = 0
    var badEmail = 0
    var badAge = 0

    nonEmptyLines("korisnici.csv").foreach { line =>
      val row = csv(line)
      val emailOk = row(1).contains("@")
      val ageOk = Try(row(2).toInt).map(age => age >= 18 && age <= 120).getOrElse(false)

      if (!emailOk) badEmail += 1
      if (!ageOk) badAge += 1
      if (emailOk && ageOk) valid += 1
    }
    println(s"Validnih korisnika: $valid")
    println(s"Neispravnih email adresa: $badEmail")
    println(s"Neispravnih godina: $badAge")
  }
}
