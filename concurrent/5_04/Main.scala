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

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val in = stdinTokens().map(_.toInt)
    val maxAttempts = in(1)
    val base = in(2)
    var success = 0
    var fail = 0
    var attemptsTotal = 0
    var delay = 0

    nonEmptyLines("naplate.csv").foreach { line =>
      val row = csv(line)
      val outcomes = row(1).split("\\|")
      var i = 0
      var done = false
      var retry = 0
      while (i < maxAttempts && i < outcomes.length && !done) {
        attemptsTotal += 1
        outcomes(i) match {
          case "OK" =>
            success += 1
            done = true
          case "TRAJNA" =>
            fail += 1
            done = true
          case "PRIVREMENA" =>
            if (i + 1 < maxAttempts && i + 1 < outcomes.length) {
              delay += base * (1 << retry)
              retry += 1
            } else {
              fail += 1
              done = true
            }
        }
        i += 1
      }
    }
    println(s"Uspesnih naplata: $success")
    println(s"Neuspesnih naplata: $fail")
    println(s"Ukupno pokusaja: $attemptsTotal")
    println(s"Ukupno logicko odlaganje: $delay")
  }
}
