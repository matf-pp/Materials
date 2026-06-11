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
    val stock = mutable.TreeMap[String, Int]()
    nonEmptyLines("stanje_skladista.csv").foreach { l => val r = csv(l); stock(r(0)) = r(1).toInt }
    val pending = mutable.Queue[(String, Int)]()
    var executed = 0

    def tryPending(): Unit = {
      var changed = true
      while (changed) {
        changed = false
        val rest = mutable.Queue[(String, Int)]()
        while (pending.nonEmpty) {
          val (item, qty) = pending.dequeue()
          if (stock.getOrElse(item, 0) >= qty) { stock(item) = stock(item) - qty; executed += 1; changed = true }
          else rest.enqueue((item, qty))
        }
        pending.enqueueAll(rest)
      }
    }
    nonEmptyLines("operacije_skladista.csv").foreach { l =>
      val r = csv(l); val typ = r(0); val item = r(2); val qty = r(3).toInt
      if (typ == "DOD") { stock(item) = stock.getOrElse(item, 0) + qty; tryPending() }
      else if (stock.getOrElse(item, 0) >= qty) { stock(item) = stock(item) - qty; executed += 1 }
      else pending.enqueue((item, qty))
    }
    println(s"Izvrseno rezervacija: $executed")
    println("Preostalo: " + fmtPairs(stock))
  }
}
