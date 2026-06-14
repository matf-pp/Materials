import java.io.File
import java.util.concurrent.{ForkJoinPool, RecursiveTask}
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
    val threshold = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val nums = nonEmptyLines("brojevi.txt").map(_.toInt).toArray

    class MaxTask(from: Int, until: Int) extends RecursiveTask[Int] {
      override def compute(): Int = {
        if (until - from <= threshold) nums.slice(from, until).max
        else {
          val mid = (from + until) / 2
          val left = new MaxTask(from, mid)
          val right = new MaxTask(mid, until)
          left.fork()
          val rightMax = right.compute()
          Math.max(left.join(), rightMax)
        }
      }
    }

    val pool = new ForkJoinPool()
    try println(s"Maksimum: ${pool.invoke(new MaxTask(0, nums.length))}")
    finally pool.shutdown()
  }
}
