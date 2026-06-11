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
    val limit = stdinTokens()(0).toInt
    val rows = nonEmptyLines("preuzimanja.csv").map(csv)
    val byHost = mutable.TreeMap[String, Int]().withDefaultValue(0)
    var mb = 0
    rows.foreach { r => byHost(r(0)) += 1; mb += r(2).toInt }
    println(s"Preuzeto datoteka: ${rows.length}")
    println(s"Ukupno MB: $mb")
    println("Najvise po hostu: " + fmtPairs(byHost.map { case (h, c) => h -> Math.min(limit, c) }))
  }
}
