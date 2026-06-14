import java.io.File
import java.util.concurrent.Semaphore
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

  def stdinLines(): List[String] = Source.stdin.getLines().toList

  def stdinTokens(): Array[String] = stdinLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val conns = stdinTokens()(0).toInt.max(1)
    val queries = nonEmptyLines("upiti_baze.txt")
    val sem = new Semaphore(conns)
    val stateLock = new Object
    val expectedPeak = Math.min(conns, queries.length)
    var active = 0
    var maxActive = 0
    var peakReached = expectedPeak == 0
    var processed = 0
    // Semafor ogranicava broj niti koje istovremeno koriste konekciju.
    val threads = queries.map { _ =>
      new Thread(new Runnable {
        def run(): Unit = {
          sem.acquire()
          try {
            stateLock.synchronized {
              active += 1
              maxActive = Math.max(maxActive, active)
              if (active >= expectedPeak) {
                peakReached = true
                stateLock.notifyAll()
              }
              while (!peakReached && active < expectedPeak) stateLock.wait()
            }
            stateLock.synchronized {
              processed += 1
              active -= 1
              stateLock.notifyAll()
            }
          } finally sem.release()
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Obradjeno upita: $processed")
    println(s"Najvise aktivnih konekcija: $maxActive")
  }
}
