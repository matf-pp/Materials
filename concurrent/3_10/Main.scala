import java.io.File
import scala.io.Source
import scala.util.Try

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

  def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val rows = nonEmptyLines("korisnici.csv").map(csv)
    val local = Array.fill(workers, 3)(0)

    // Svaka nit lokalno broji ispravne zapise i tipove gresaka.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * rows.length / workers
          val until = (index + 1) * rows.length / workers
          rows.slice(from, until).foreach { row =>
            val emailOk = row(1).contains("@")
            val ageOk = Try(row(2).toInt).map(age => age >= 18 && age <= 120).getOrElse(false)

            if (emailOk && ageOk) local(index)(0) += 1
            if (!emailOk) local(index)(1) += 1
            if (!ageOk) local(index)(2) += 1
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val valid = local.map(_(0)).sum
    val badEmail = local.map(_(1)).sum
    val badAge = local.map(_(2)).sum
    println(s"Validnih korisnika: $valid")
    println(s"Neispravnih email adresa: $badEmail")
    println(s"Neispravnih godina: $badAge")
  }
}
