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

  def main(args: Array[String]): Unit = {
    val counts = mutable.Map('A' -> 0, 'G' -> 0, 'C' -> 0, 'T' -> 0)
    readLines("dnk.biodata").mkString.toUpperCase.foreach(c => if (counts.contains(c)) counts(c) += 1)
    println(s"A=${counts('A')}, G=${counts('G')}, C=${counts('C')}, T=${counts('T')}")
  }
}
