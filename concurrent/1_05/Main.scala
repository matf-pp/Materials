import scala.io.Source

object Main {

  class ProcessThread(
      val index: Int,
      val command: String,
      val statuses: Array[Int]
  ) extends Thread {

    override def run(): Unit = {
      val process = new ProcessBuilder(command.split(" ").toSeq: _*)
        .inheritIO()
        .start()

      val exitCode = process.waitFor()

      statuses(index) = exitCode
    }
  }

  def main(args: Array[String]): Unit = {
    val commands = Source.fromFile("procesi_status.txt")
      .getLines()
      .toArray

    val statuses = new Array[Int](commands.length)
    val threads = new Array[ProcessThread](commands.length)

    for (i <- commands.indices) {
      threads(i) = new ProcessThread(
        i,
        commands(i),
        statuses
      )

      threads(i).start()
    }

    for (thread <- threads) {
      thread.join()
    }

    var successful = 0

    for (status <- statuses) {
      if (status == 0)
        successful += 1
    }

    val unsuccessful = statuses.length - successful

    println(s"Uspesnih procesa: $successful")
    println(s"Neuspesnih procesa: $unsuccessful")

    val statusString = statuses.zipWithIndex
      .map { case (status, i) => s"${i + 1}=$status" }
      .mkString(", ")

    println(s"Statusi: $statusString")
  }
}

