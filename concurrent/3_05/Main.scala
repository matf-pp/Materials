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

  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val counts = mutable.TreeMap[String, Int]().withDefaultValue(0)
    readLines("tekst.txt").foreach { line =>
      val words = line.toLowerCase
        .replaceAll("[^a-z0-9]+", " ")
        .split("\\s+")
        .filter(_.nonEmpty)

      words.foreach(word => counts(word) += 1)
    }
    println(fmtPairs(counts))
  }
}
