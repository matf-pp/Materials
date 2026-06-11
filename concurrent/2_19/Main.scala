import scala.collection.mutable
import scala.io.Source

case class OrchardRow(fruit: String, trees: Long, kgPerTree: Long)

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  private def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)

  private def readRows(): Vector[OrchardRow] = {
    val source = Source.fromFile("drvoredi.txt")
    try {
      source.getLines().filter(_.trim.nonEmpty).map { line =>
        val parts = csv(line)
        OrchardRow(parts(0), parts(1).toLong, parts(2).toLong)
      }.toVector
    } finally source.close()
  }

  private def formatPairs(values: Iterable[(String, Long)]): String =
    values.map { case (key, value) => s"$key=$value" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val workerCount = stdinTokens().headOption.map(_.toInt).getOrElse(1) max 1
    val lock = new Object
    val queue = mutable.Queue[OrchardRow](readRows(): _*)
    val storage = mutable.TreeMap[String, Long]()

    // Monitor stiti zajednicki red drvoreda i zbir po vrstama voca.
    val workers = (0 until workerCount).map { _ =>
      new Thread(new Runnable {
        override def run(): Unit = {
          var active = true
          while (active) {
            lock.synchronized {
              if (queue.isEmpty) {
                active = false
              } else {
                val row = queue.dequeue()
                val amount = row.trees * row.kgPerTree
                storage(row.fruit) = storage.getOrElse(row.fruit, 0L) + amount
              }
            }
          }
        }
      })
    }

    workers.foreach(_.start())
    workers.foreach(_.join())

    println(s"Ukupno obrano: ${storage.values.sum}")
    println(s"Po vocu: ${formatPairs(storage)}")
  }
}
