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
    val ttl = stdinTokens()(1).toInt
    val source = nonEmptyLines("vrednosti_kesa.csv").map(csv).map(r => r(0) -> r(1)).toMap
    val cache = mutable.Map[String, (String, Int)]()
    var loads = 0
    var processed = 0

    nonEmptyLines("zahtevi_kesa.csv").map(csv).sortBy(_(0).toInt).foreach { row =>
      val time = row(0).toInt
      val key = row(1)
      processed += 1
      cache.get(key) match {
        case Some((_, loaded)) if time - loaded < ttl =>
          ()
        case _ =>
          cache(key) = (source(key), time)
          loads += 1
      }
    }
    println(s"Obradjeno zahteva: $processed")
    println(s"Ucitavanja izvora: $loads")
    println("Rezultati: " + fmtPairs(mutable.TreeMap(cache.toSeq.map { case (k, (v, _)) => k -> v }: _*)))
  }
}
