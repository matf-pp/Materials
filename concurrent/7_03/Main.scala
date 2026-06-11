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

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val in = stdinTokens().map(_.toInt); val k = in(1)
    val counts = mutable.Map[Char, Int]().withDefaultValue(0)
    readLines("manifest.data").mkString("\n").toLowerCase.filter(c => c >= 'a' && c <= 'z').foreach(c => counts(c) += 1)
    println(counts.toSeq.sortBy { case (c, n) => (-n, c) }.take(k).map { case (c, n) => s"$c=$n" }.mkString(", "))
  }
}
