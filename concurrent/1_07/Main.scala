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
    val files = nonEmptyLines("datoteke.txt")
    val totals = Array.fill(files.length)((0, 0, 0))
    // Obrada jedne datoteke ne deli stanje sa obradom druge datoteke.
    val threads = files.zipWithIndex.map { case (file, index) =>
      new Thread(new Runnable {
        def run(): Unit = {
          val lines = readLines(file)
          totals(index) = (1, lines.length, lines.map(_.length).sum)
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Ukupno datoteka: ${totals.map(_._1).sum}")
    println(s"Ukupno linija: ${totals.map(_._2).sum}")
    println(s"Ukupno znakova: ${totals.map(_._3).sum}")
  }
}
