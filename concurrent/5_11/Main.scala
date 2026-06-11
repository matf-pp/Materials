import java.io.File
import scala.collection.mutable
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
    val flags = mutable.TreeMap[String, String]()
    nonEmptyLines("flegovi.csv").foreach { line =>
      val row = csv(line)
      flags(row(0)) = row(1)
    }
    val initial = flags.size
    var changes = 0
    nonEmptyLines("promene_flegova.csv").foreach { line =>
      val row = csv(line)
      flags(row(0)) = row(1)
      changes += 1
    }
    println(s"Pocetnih flegova: $initial")
    println(s"Promena: $changes")
    println("Ukljuceni flegovi: " + flags.collect { case (k, "ON") => k }.mkString(", "))
  }
}
