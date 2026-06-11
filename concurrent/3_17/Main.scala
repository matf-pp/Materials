import scala.io.Source

case class Stats(product: BigInt, sum: BigInt, negative: Int, positive: Int, count: Int) {
  def +(other: Stats): Stats =
    Stats(
      product * other.product,
      sum + other.sum,
      negative + other.negative,
      positive + other.positive,
      count + other.count
    )
}

object Stats {
  val empty: Stats = Stats(BigInt(1), BigInt(0), 0, 0, 0)
}

object Main {
  private def stdinLines(): List[String] =
    Source.stdin.getLines().map(_.trim).filter(_.nonEmpty).toList

  private def readVector(path: String): Vector[Long] = {
    val source = Source.fromFile(path)
    try source.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).map(_.toLong).toVector
    finally source.close()
  }

  private def statsOf(values: Seq[Long]): Stats =
    values.foldLeft(Stats.empty) { (acc, value) =>
      Stats(
        acc.product * BigInt(value),
        acc.sum + BigInt(value),
        acc.negative + (if (value < 0) 1 else 0),
        acc.positive + (if (value > 0) 1 else 0),
        acc.count + 1
      )
    }

  private def dataParallelStats(values: Vector[Long], threadCount: Int): Stats = {
    val workers = threadCount max 1
    val partial = Array.fill(workers)(Stats.empty)

    // Svaka nit racuna statistiku svog segmenta, a parcijalni rezultati se spajaju na kraju.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        override def run(): Unit = {
          val from = index * values.length / workers
          val until = (index + 1) * values.length / workers
          partial(index) = statsOf(values.slice(from, until))
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())
    partial.foldLeft(Stats.empty)(_ + _)
  }

  private def taskParallelStats(values: Vector[Long]): Stats = {
    var product = BigInt(1)
    var sum = BigInt(0)
    var negative = 0
    var positive = 0

    // Ovde su poslovi nezavisni: proizvod, suma i prebrojavanja rade nad istim ulazom.
    val tasks = Seq(
      new Thread(new Runnable {
        override def run(): Unit = product = values.foldLeft(BigInt(1))((acc, value) => acc * BigInt(value))
      }),
      new Thread(new Runnable {
        override def run(): Unit = sum = values.foldLeft(BigInt(0))((acc, value) => acc + BigInt(value))
      }),
      new Thread(new Runnable {
        override def run(): Unit = negative = values.count(_ < 0)
      }),
      new Thread(new Runnable {
        override def run(): Unit = positive = values.count(_ > 0)
      })
    )

    tasks.foreach(_.start())
    tasks.foreach(_.join())
    Stats(product, sum, negative, positive, values.length)
  }

  private def average(stats: Stats): Double =
    if (stats.count == 0) 0.0 else stats.sum.toDouble / stats.count

  private def printStats(file: String, stats: Stats, matches: Boolean): Unit = {
    println(s"Datoteka: $file")
    println(s"Proizvod: ${stats.product}")
    println(s"Suma: ${stats.sum}")
    println(f"Prosecna vrednost: ${average(stats)}%.2f")
    println(s"Broj negativnih: ${stats.negative}")
    println(s"Broj pozitivnih: ${stats.positive}")
    println(s"Poklapanje pristupa: ${if (matches) "DA" else "NE"}")
  }

  def main(args: Array[String]): Unit = {
    val lines = stdinLines()
    val threadCount = lines.headOption.map(_.toInt).getOrElse(1) max 1
    val files = lines.drop(1)

    files.foreach { file =>
      val values = readVector(file)
      val taskStart = System.nanoTime()
      val taskStats = taskParallelStats(values)
      val taskElapsed = System.nanoTime() - taskStart
      val dataStart = System.nanoTime()
      val dataStats = dataParallelStats(values, threadCount)
      val dataElapsed = System.nanoTime() - dataStart
      val _ = taskElapsed + dataElapsed
      printStats(file, dataStats, dataStats == taskStats)
    }
  }
}
