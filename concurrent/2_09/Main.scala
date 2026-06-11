import java.io.File
import java.util.concurrent.CyclicBarrier
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

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens()(0).toInt
    val nums = nonEmptyLines("delovi.txt").map(_.toInt)
    val barrier = new CyclicBarrier(workers, new Runnable { def run(): Unit = println("Svi delovi su spremni") })
    val sums = new Array[Int](workers)
    val chunk = Math.ceil(nums.length.toDouble / workers).toInt.max(1)
    // Barijera odlozi sabiranje ukupnog rezultata dok svaki deo ne bude spreman.
    val threads = (0 until workers).map { i =>
      new Thread(new Runnable { def run(): Unit = {
        val from = i * chunk
        val until = Math.min((i + 1) * chunk, nums.length)
        sums(i) = nums.slice(from, until).sum
        barrier.await()
      }})
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Zbir: ${sums.sum}")
  }
}
