import java.io.File
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

  def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val nums = nonEmptyLines("ocene.txt").map(_.toInt)
    val local = Array.fill(workers, 6)(0)
    // Svaka nit pravi lokalni histogram ocena za svoj blok.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * nums.length / workers
          val until = (index + 1) * nums.length / workers
          nums.slice(from, until).foreach(grade => local(index)(grade) += 1)
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val histogram = (1 to 5).map(grade => local.map(_(grade)).sum)
    println("Histogram: " + (1 to 5).zip(histogram).map { case (grade, count) => s"$grade=$count" }.mkString(", "))
  }
}
