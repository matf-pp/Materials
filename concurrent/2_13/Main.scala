import java.io.File
import java.util.concurrent.CyclicBarrier
import scala.io.Source

object Main {
  def readLines(file: String): List[String] = {
    val f = new File(file)
    if (!f.exists()) Nil
    else {
      val src = Source.fromFile(f)
      try src.getLines().toList finally src.close()
    }
  }

  def nonEmptyLines(file: String): List[String] =
    readLines(file).map(_.trim).filter(_.nonEmpty)

  def csv(line: String): Array[String] =
    line.split(",", -1).map(_.trim)

  def stdinTokens(): Array[String] =
    Source.stdin.getLines()
      .flatMap(_.trim.split("\\s+").filter(_.nonEmpty))
      .toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val rows = nonEmptyLines("faze_uvoza.csv").map(csv)

    val validation = new Array[Int](workers)
    val preparation = new Array[Int](workers)
    val writes = new Array[Int](workers)

    val barrier = new CyclicBarrier(workers)

    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * rows.length / workers
          val until = (index + 1) * rows.length / workers
          val localRows = rows.slice(from, until)

          // Faza 1: validacija
          validation(index) =
            localRows.map(_(1).toInt).sum

          barrier.await()

          // Faza 2: priprema
          preparation(index) =
            localRows.map(_(2).toInt).sum

          barrier.await()

          // Faza 3: upis
          writes(index) =
            localRows.map(_(3).toInt).sum
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    println(s"Validirano: ${validation.sum}")
    println(s"Pripremljeno: ${preparation.sum}")
    println(s"Upisano: ${writes.sum}")
  }
}
