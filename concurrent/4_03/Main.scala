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

  def pipe(line: String): Array[String] = line.split("\\|", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("dogadjaji.txt").map(pipe)
    val err = rows.count(r => r.length > 1 && r(1).toLowerCase.contains("greska"))
    println(s"Obradjeno dogadjaja: ${rows.length}")
    println(s"GRESKA=$err, INFO=${rows.length - err}")
  }
}
