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
    val seen = mutable.TreeSet[String]()
    var dup = 0
    nonEmptyLines("poslovi_idempotentno.csv").foreach { line =>
      val id = csv(line)(0)
      if (seen(id)) dup += 1 else seen += id
    }
    println(s"Obradjeno jedinstvenih poslova: ${seen.size}")
    println(s"Preskoceno duplikata: $dup")
    println("Id-jevi: " + seen.mkString(", "))
  }
}
