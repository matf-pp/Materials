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
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("obavestenja_spawn.csv").map(csv)
    val sent = Array.fill(rows.length)(("", false))
    // Niti samo upisuju lokalni ishod slanja; agregacija je posle njihovog zavrsetka.
    val threads = rows.zipWithIndex.map { case (row, index) =>
      new Thread(new Runnable {
        def run(): Unit = {
          sent(index) = (row(1), row(2) == "VISOK")
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val byChannel = mutable.TreeMap[String, Int]().withDefaultValue(0)
    sent.foreach { case (channel, _) => byChannel(channel) += 1 }
    val high = sent.count(_._2)
    println(s"Poslato obavestenja: ${rows.length}")
    println(s"Visokog prioriteta: $high")
    println("Po kanalima: " + fmtPairs(byChannel))
  }
}
