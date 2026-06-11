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

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val input = stdinTokens()
    val ids = input.drop(1).map(_.toInt)
    var loads = 0
    // Lazy vrednost ucitava katalog tek kada prvi zahtev zatrazi proizvod.
    lazy val products: Map[Int, String] = {
      loads += 1
      nonEmptyLines("proizvodi.csv").map(csv).map(r => r(0).toInt -> r(1)).toMap
    }
    val results = mutable.LinkedHashMap[Int, String]()
    ids.foreach(id => results.getOrElseUpdate(id, products(id)))
    println(s"Ucitavanja datoteke: $loads")
    println("Rezultati: " + fmtPairs(results))
  }
}
