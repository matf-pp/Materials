import java.io.File
import java.util.concurrent.Semaphore
import scala.collection.mutable
import scala.io.Source

case class HostState(semaphore: Semaphore, expectedPeak: Int) {
  val lock = new Object
  var active: Int = 0
  var maxActive: Int = 0
  var peakReached: Boolean = expectedPeak == 0
}

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

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val limit = stdinTokens()(0).toInt.max(1)
    val rows = nonEmptyLines("preuzimanja.csv").map(csv)
    // Broj redova po hostu nam govori koliki paralelizam uopste moze da se dostigne.
    val countsByHost = rows.groupBy(_(0)).map { case (host, hostRows) => host -> hostRows.length }
    val states = countsByHost.map { case (host, count) =>
      host -> HostState(new Semaphore(limit), Math.min(limit, count))
    }
    // Svaki host ima sopstveni semafor i sopstveno merenje najveceg paralelizma.
    val threads = rows.map { row =>
      new Thread(new Runnable {
        def run(): Unit = {
          val state = states(row(0))
          state.semaphore.acquire()
          try {
            state.lock.synchronized {
              state.active += 1
              state.maxActive = Math.max(state.maxActive, state.active)
              if (state.active >= state.expectedPeak) {
                state.peakReached = true
                state.lock.notifyAll()
              }
              while (!state.peakReached && state.active < state.expectedPeak) state.lock.wait()
            }
            state.lock.synchronized {
              state.active -= 1
              state.lock.notifyAll()
            }
          } finally state.semaphore.release()
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val byHost = mutable.TreeMap[String, Int]()
    states.foreach { case (host, state) => byHost(host) = state.maxActive }
    val mb = rows.map(_(2).toInt).sum
    println(s"Preuzeto datoteka: ${rows.length}")
    println(s"Ukupno MB: $mb")
    println("Najvise po hostu: " + fmtPairs(byHost))
  }
}
