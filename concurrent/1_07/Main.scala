import scala.io.Source

object Main {

  class CheckThread(
      val index: Int,
      val id: String,
      val dependencies: Array[CheckThread],
      val command: String,
      val results: Array[String],
      val statuses: Array[Int]
  ) extends Thread {

    override def run(): Unit = {
      // Wait for all dependent checks to finish.
      for (dependency <- dependencies) {
        dependency.join()
      }

      // Run the command as an operating-system process.
      val process = new ProcessBuilder(command.split(" ").toSeq: _*)
        .redirectErrorStream(true)
        .start()

      // Save the output.
      val output = scala.io.Source.fromInputStream(process.getInputStream)
        .getLines()
        .mkString("\n")

      val exitCode = process.waitFor()

      results(index) = output
      statuses(index) = exitCode
    }
  }

  def main(args: Array[String]): Unit = {
    val lines = Source.fromFile("provere.txt")
      .getLines()
      .toArray

    val n = lines.length

    val results = new Array[String](n)
    val statuses = new Array[Int](n)
    val threads = new Array[CheckThread](n)

    // First parse all entries so that dependency threads can be referenced.
    val entries = lines.map { line =>
      val parts = line.split("\\|", 3)

      val id = parts(0)
      val dependencies =
        if (parts(1) == "-")
          Array.empty[String]
        else
          parts(1).split(",")

      val command = parts(2)

      (id, dependencies, command)
    }

    val indexById = entries.zipWithIndex.map {
      case ((id, _, _), index) => id -> index
    }.toMap

    // Create all threads.
    for (i <- 0 until n) {
      val (id, dependencyIds, command) = entries(i)

      val dependencies = dependencyIds.map { dependencyId =>
        threads(indexById(dependencyId))
      }

      threads(i) = new CheckThread(
        i,
        id,
        dependencies,
        command,
        results,
        statuses
      )
    }

    // Start all threads.
    for (thread <- threads) {
      thread.start()
    }

    // Wait for all checks to finish.
    for (thread <- threads) {
      thread.join()
    }

    // Count successful checks.
    var successful = 0

    for (status <- statuses) {
      if (status == 0) {
        successful += 1
      }
    }

    println(s"Uspesnih provera: $successful")

    val output = entries.indices.map { i =>
      s"${entries(i)._1}=${results(i)}"
    }.mkString(", ")

    println(s"Rezultati: $output")
  }
}

