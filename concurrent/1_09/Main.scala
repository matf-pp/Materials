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

  def main(args: Array[String]): Unit = {
    val files = nonEmptyLines("datoteke_provere.txt")
    var existing = 0; var missing = 0; var nonempty = 0; var chars = 0
    files.foreach { f =>
      val file = new File(f)
      if (file.exists()) {
        existing += 1
        val c = readLines(f).map(_.length).sum
        chars += c
        if (c > 0) nonempty += 1
      } else missing += 1
    }
    println(s"Postojecih datoteka: $existing")
    println(s"Nedostajucih datoteka: $missing")
    println(s"Nepraznih datoteka: $nonempty")
    println(s"Ukupno znakova: $chars")
  }
}
