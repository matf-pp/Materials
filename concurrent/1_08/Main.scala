import java.io.File
import java.util.concurrent.TimeUnit
import scala.io.Source
import scala.util.Try

case class ProcResult(code: Int, output: String, timedOut: Boolean)

object Main {
  def readLines(file: String): List[String] = {
    val f = new File(file)
    if (!f.exists()) Nil else {
      val src = Source.fromFile(f)
      try src.getLines().toList finally src.close()
    }
  }

  def nonEmptyLines(file: String): List[String] = readLines(file).map(_.trim).filter(_.nonEmpty)

  def splitCommand(command: String): Array[String] = command.trim.split("\\s+").filter(_.nonEmpty)

  def runCommand(command: String, timeoutMs: Long = -1L): ProcResult = {
    val parts = splitCommand(command)
    if (parts.isEmpty) ProcResult(0, "", false)
    else {
      val p = new ProcessBuilder(parts: _*).redirectErrorStream(true).start()
      val finished =
        if (timeoutMs >= 0) p.waitFor(timeoutMs, TimeUnit.MILLISECONDS)
        else {
          p.waitFor()
          true
        }
      if (!finished) {
        p.destroyForcibly()
        p.waitFor()
        val out = Try(Source.fromInputStream(p.getInputStream).mkString.trim).getOrElse("")
        ProcResult(-1, out, true)
      } else {
        val out = Try(Source.fromInputStream(p.getInputStream).mkString.trim).getOrElse("")
        ProcResult(p.exitValue(), out, false)
      }
    }
  }

  def pipe(line: String): Array[String] = line.split("\\|", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("komande_izlaza.csv").map(pipe)
    val results = Array.fill(rows.length)(("", 0, ""))
    // Rezultat svake komande ostaje vezan za njen ulazni indeks.
    val threads = rows.zipWithIndex.map { case (row, index) =>
      new Thread(new Runnable {
        def run(): Unit = {
          val pr = runCommand(row(1))
          val first = pr.output.split("\\r?\\n").headOption.getOrElse("")
          results(index) = (row(0), pr.code, first)
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Procesa: ${results.length}")
    println(s"Uspesnih procesa: ${results.count(_._2 == 0)}")
    println("Izlazi: " + results.map(r => s"${r._1}=${r._3}").mkString(", "))
  }
}
