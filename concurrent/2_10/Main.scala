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
    nonEmptyLines("stanje_skladista.csv").foreach { line =>
      val row = csv(line)
      stock(row(0)) = row(1).toInt
    }
    val monitor = new Object
    var executed = 0
    val operations = nonEmptyLines("operacije_skladista.csv").map(csv)
    // Rezervacije cekaju na monitoru dok dopuna ne obezbedi dovoljno komada.
    val threads = operations.map { row =>
      new Thread(new Runnable {
        def run(): Unit = monitor.synchronized {
          val typ = row(0)
          val item = row(2)
          val qty = row(3).toInt
          if (typ == "DOD") {
            stock(item) = stock.getOrElse(item, 0) + qty
            monitor.notifyAll()
          } else {
            while (stock.getOrElse(item, 0) < qty) monitor.wait()
            stock(item) = stock(item) - qty
            executed += 1
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Izvrseno rezervacija: $executed")
    println("Preostalo: " + fmtPairs(stock))
  }
}
