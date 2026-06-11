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

  def nonEmptyLines(file: String): List[String] = readLines(file).map(_.trim).filter(_.nonEmpty)

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val threshold = stdinTokens()(1).toInt
    val consec = mutable.Map[String, Int]().withDefaultValue(0)
    val open = mutable.TreeSet[String]()
    var okc = 0
    var fail = 0
    var skip = 0

    nonEmptyLines("zahtevi_servisa.csv").foreach { line =>
      val row = csv(line)
      val service = row(0)
      if (open(service)) {
        skip += 1
      } else if (row(1) == "OK") {
        okc += 1
        consec(service) = 0
      } else {
        fail += 1
        consec(service) += 1
        if (consec(service) >= threshold) open += service
      }
    }
    println(s"Uspesnih zahteva: $okc")
    println(s"Neuspesnih zahteva: $fail")
    println(s"Preskocenih zahteva: $skip")
    println("Otvorena kola: " + open.mkString(", "))
  }
}
