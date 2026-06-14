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
    val in = stdinTokens().map(_.toInt)
    val workers = in(0).max(1)
    val k = in(1)
    val counts = mutable.Map[Char, Int]().withDefaultValue(0)
    val lock = new Object
    val lines = readLines("manifest.data")
    // Svaka nit obradjuje disjunktan skup linija i zatim spaja lokalni broj karaktera.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val local = mutable.Map[Char, Int]().withDefaultValue(0)
          val from = index * lines.length / workers
          val until = (index + 1) * lines.length / workers
          lines.slice(from, until).mkString("\n").toLowerCase
            .filter(c => c >= 'a' && c <= 'z')
            .foreach(c => local(c) += 1)
          lock.synchronized {
            local.foreach { case (char, count) => counts(char) += count }
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(counts.toSeq.sortBy { case (c, n) => (-n, c) }.take(k).map { case (c, n) => s"$c=$n" }.mkString(", "))
  }
}
