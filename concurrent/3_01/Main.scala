import scala.io.Source

object Main {

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def main(args: Array[String]): Unit = {
    val lines = stdinLines(); val n = lines(0).trim.toInt; val values = lines(1).trim.split("\\s+").map(_.toInt).take(n)
    println(s"Suma: ${values.sum}")
  }
}
