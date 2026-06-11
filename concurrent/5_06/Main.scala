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
    val groups = mutable.TreeMap[String, (Int, Int)]()
    var okc = 0
    var bad = 0

    nonEmptyLines("bulkhead_zahtevi.csv").foreach { line =>
      val row = csv(line)
      val group = row(0)
      val old = groups.getOrElse(group, (0, 0))
      if (row(2) == "OK") {
        okc += 1
        groups(group) = (old._1 + 1, old._2)
      } else {
        bad += 1
        groups(group) = (old._1, old._2 + 1)
      }
    }
    println(s"Uspesnih zahteva: $okc")
    println(s"Neuspesnih zahteva: $bad")
    println("Po grupama: " + groups.map { case (g, (o, b)) => s"$g=$o/$b" }.mkString(", "))
  }
}
