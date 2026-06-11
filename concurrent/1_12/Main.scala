import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Main {
  private def stdinTokens(): List[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toList

  def main(args: Array[String]): Unit = {
    val tableCount = stdinTokens().headOption.map(_.toInt).getOrElse(0)
    val logs = Array.fill(5)(ArrayBuffer[String]())

    // Svaki konobar pise u svoj dnevnik, pa nema deljenog upisa izmedju niti.
    val threads = (0 until 5).map { waiterIndex =>
      new Thread(new Runnable {
        override def run(): Unit = {
          var table = waiterIndex + 1
          while (table <= tableCount) {
            logs(waiterIndex) += s"Konobar ${waiterIndex + 1} usluzio sto $table"
            table += 5
          }
          logs(waiterIndex) += s"Konobar ${waiterIndex + 1} zavrsio posao"
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    logs.foreach(_.foreach(println))
  }
}
