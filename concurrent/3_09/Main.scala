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

  def main(args: Array[String]): Unit = {
    val xs = nonEmptyLines("latencije.txt").map(_.toInt).sorted
    val a = xs.count(_ <= 100); val b = xs.count(x => x >= 101 && x <= 500); val c = xs.count(_ > 500)
    val p95Index = Math.ceil(xs.length * 0.95).toInt - 1
    println(s"Zahteva: ${xs.length}")
    println(s"Do 100 ms: $a, 101-500 ms: $b, Preko 500 ms: $c")
    println(s"P95: ${xs(Math.max(0, p95Index))}")
  }
}
