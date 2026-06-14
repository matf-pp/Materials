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
    val files = nonEmptyLines("datoteke_provere.txt")
    val results = Array.fill(files.length)((0, 0, 0, 0))
    // Svaka putanja se proverava nezavisno, a zbir se racuna posle zavrsetka niti.
    val threads = files.zipWithIndex.map { case (path, index) =>
      new Thread(new Runnable {
        def run(): Unit = {
          val file = new File(path)
          if (file.exists()) {
            val c = readLines(path).map(_.length).sum
            results(index) = (1, 0, if (c > 0) 1 else 0, c)
          } else {
            results(index) = (0, 1, 0, 0)
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val existing = results.map(_._1).sum
    val missing = results.map(_._2).sum
    val nonempty = results.map(_._3).sum
    val chars = results.map(_._4).sum
    println(s"Postojecih datoteka: $existing")
    println(s"Nedostajucih datoteka: $missing")
    println(s"Nepraznih datoteka: $nonempty")
    println(s"Ukupno znakova: $chars")
  }
}
