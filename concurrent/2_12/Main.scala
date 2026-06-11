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

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val reserved = mutable.TreeSet[String]()
    var okCount = 0; var badCount = 0
    nonEmptyLines("rezervacije_sedista.csv").foreach { l =>
      val r = csv(l); val seat = r(2)
      if (reserved.contains(seat)) badCount += 1 else { reserved += seat; okCount += 1 }
    }
    println(s"Prihvaceno rezervacija: $okCount")
    println(s"Odbijeno rezervacija: $badCount")
    println("Zauzeta sedista: " + reserved.mkString(", "))
  }
}
