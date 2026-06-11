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
    val rows = nonEmptyLines("zadaci.csv").map(csv)
    val ids = rows.map(_(0)).toSet
    val dur = rows.map(r => r(0) -> r(1).toInt).toMap
    val deps = rows.map(r => r(0) -> (if (r(2) == "-") Set[String]() else r(2).split("\\|").toSet)).toMap
    val done = mutable.Set[String]()
    var changed = true

    // U svakom prolazu se dodaju zadaci cije su zavisnosti vec zavrsene.
    while (changed) {
      changed = false
      ids.diff(done.toSet).foreach { id =>
        if (deps(id).subsetOf(done.toSet)) {
          done += id
          changed = true
        }
      }
    }
    println(s"Zavrseno zadataka: ${done.size}")
    println(s"Ukupan rad: ${done.toList.map(dur).sum}")
    println(s"Neizvrseno zbog ciklusa: ${ids.size - done.size}")
  }
}
