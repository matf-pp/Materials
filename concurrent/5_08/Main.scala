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
    val bal = mutable.TreeMap[String, Int]()
    nonEmptyLines("racuni_prenosa.csv").foreach { line =>
      val row = csv(line)
      bal(row(0)) = row(1).toInt
    }
    var okc = 0
    var bad = 0

    nonEmptyLines("prenosi.csv").foreach { line =>
      val row = csv(line)
      val from = row(0)
      val to = row(1)
      val amount = row(2).toInt
      if (bal(from) >= amount) {
        bal(from) -= amount
        bal(to) = bal.getOrElse(to, 0) + amount
        okc += 1
      } else {
        bad += 1
      }
    }
    println(s"Izvrseno prenosa: $okc")
    println(s"Odbijeno prenosa: $bad")
    println("Stanja: " + fmtPairs(bal))
  }
}
