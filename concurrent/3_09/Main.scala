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
    val xs = nonEmptyLines("latencije.txt").map(_.toInt).sorted
    val local = Array.fill(workers, 3)(0)
    // Klasifikacija latencija je lokalna po bloku; percentil se racuna iz objedinjenog niza.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * xs.length / workers
          val until = (index + 1) * xs.length / workers
          xs.slice(from, until).foreach { x =>
            if (x <= 100) local(index)(0) += 1
            else if (x <= 500) local(index)(1) += 1
            else local(index)(2) += 1
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val a = local.map(_(0)).sum
    val b = local.map(_(1)).sum
    val c = local.map(_(2)).sum
    val p95Index = Math.ceil(xs.length * 0.95).toInt - 1
    println(s"Zahteva: ${xs.length}")
    println(s"Do 100 ms: $a, 101-500 ms: $b, Preko 500 ms: $c")
    println(s"P95: ${xs(Math.max(0, p95Index))}")
  }
}
