import java.io.File
import java.util.concurrent.{Semaphore, ThreadLocalRandom}
import scala.collection.mutable
import scala.io.Source

case class HostState(semaphore: Semaphore) {
  val lock = new Object
  var active: Int = 0
  var maxActive: Int = 0
}

object Main {
  def readLines(file: String): List[String] = {
    val f = new File(file)
    if (!f.exists()) Nil
    else {
      val src = Source.fromFile(f)
      try src.getLines().toList finally src.close()
    }
  }

  def nonEmptyLines(file: String): List[String] =
    readLines(file).map(_.trim).filter(_.nonEmpty)

  def stdinLines(): List[String] =
    Source.stdin.getLines().toList

  def stdinTokens(): Array[String] =
    stdinLines()
      .flatMap(_.trim.split("\\s+").filter(_.nonEmpty))
      .toArray

  def csv(line: String): Array[String] =
    line.split(",", -1).map(_.trim)

  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String =
    pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val limit = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val rows = nonEmptyLines("preuzimanja.csv").map(csv)

    // Svaki host ima sopstveni brojački semafor.
    val hosts = rows.map(_(0)).distinct
    val states = hosts.map { host =>
      host -> HostState(new Semaphore(limit))
    }.toMap

    // Svako preuzimanje izvršava se u posebnoj niti.
    val threads = rows.map { row =>
      new Thread(new Runnable {
        def run(): Unit = {
          val state = states(row(0))

          // Čekanje dok za dati host ne postane dostupna konekcija.
          state.semaphore.acquire()

          try {
            state.lock.synchronized {
              state.active += 1
              state.maxActive = Math.max(state.maxActive, state.active)
            }

            // Simulacija preuzimanja: 5 do 10 sekundi.
            Thread.sleep(
              ThreadLocalRandom.current().nextInt(5, 11) * 1000L
            )

            state.lock.synchronized {
              state.active -= 1
            }
          } finally {
            state.semaphore.release()
          }
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    val byHost = mutable.TreeMap[String, Int]()
    states.foreach { case (host, state) =>
      byHost(host) = state.maxActive
    }

    val mb = rows.map(_(2).toInt).sum

    println(s"Preuzeto datoteka: ${rows.length}")
    println(s"Ukupno MB: $mb")
    println("Najvise po hostu: " + fmtPairs(byHost))
  }
}
