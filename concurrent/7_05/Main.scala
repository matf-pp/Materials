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
    val rows = nonEmptyLines("ucesnici.info")
    val n = rows.headOption.map(_.toInt).getOrElse(0)
    val participants = rows.drop(1).take(n).map { line =>
      val parts = line.split("\\s+", 3)
      (parts(0), parts.lift(1).getOrElse(""), parts.lift(2).getOrElse(""))
    }
    val prizes =
      Seq("Kopaonik 10 dana") ++
        Seq.fill(3)("Srebrno jezero 7 dana") ++
        Seq.fill(5)("Vikend na Tari")

    (participants zip prizes).foreach { case ((_, ime, prezime), prize) =>
      println(s"$ime $prezime: $prize")
    }
  }
}
