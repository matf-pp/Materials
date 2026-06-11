import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def main(args: Array[String]): Unit = {
    val lines = stdinLines(); val n = lines(0).trim.toInt
    val a = lines(1).trim.split("\\s+").map(_.toInt).take(n); val b = lines(2).trim.split("\\s+").map(_.toInt).take(n)
    println(s"Skalarni proizvod: ${(a zip b).map { case (x, y) => x * y }.sum}")
  }
}
