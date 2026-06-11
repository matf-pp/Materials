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

  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val catalog = mutable.TreeMap[String, String]()
    nonEmptyLines("katalog.txt").foreach { line =>
      val p = line.split("\\s+", 3)
      if (p(0) == "W" && p.length >= 3) catalog(p(1)) = p(2)
    }
    println("Katalog: " + fmtPairs(catalog))
  }
}
