import java.io.File
import java.util.concurrent.locks.ReentrantReadWriteLock
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

  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val catalog = mutable.TreeMap[String, String]()
    val lock = new ReentrantReadWriteLock()
    val operations = nonEmptyLines("katalog.txt").map(_.split("\\s+", 3))
    // Citanja dele bravu za citace, dok upisi zauzimaju iskljucivu bravu.
    val threads = operations.map { parts =>
      new Thread(new Runnable {
        def run(): Unit = {
          if (parts(0) == "R" && parts.length >= 2) {
            val readLock = lock.readLock()
            readLock.lock()
            try catalog.get(parts(1))
            finally readLock.unlock()
          } else if (parts(0) == "W" && parts.length >= 3) {
            val writeLock = lock.writeLock()
            writeLock.lock()
            try catalog(parts(1)) = parts(2)
            finally writeLock.unlock()
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println("Katalog: " + fmtPairs(catalog))
  }
}
