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
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("obavestenja_spawn.csv").map(csv)
    val byChannel = mutable.TreeMap[String, Int]().withDefaultValue(0)
    var high = 0
    rows.foreach { r => byChannel(r(1)) += 1; if (r(2) == "VISOK") high += 1 }
    println(s"Poslato obavestenja: ${rows.length}")
    println(s"Visokog prioriteta: $high")
    println("Po kanalima: " + fmtPairs(byChannel))
  }
}
