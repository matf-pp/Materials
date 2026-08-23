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

  def pipe(line: String): Array[String] = line.split("\\,", -1).map(_.trim)
  def fmtPairs[A, B](pairs: Iterable[(A, B)]): String = pairs.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("zadaci.csv").map(pipe)
    val statuses = Array.fill(rows.length)(("", ""))
    // Svaka komanda ima svoju nit i svoj rok izvrsavanja.
    val threads = rows.zipWithIndex.map { case (row, index) =>
      new Thread(new Runnable {
        def run(): Unit = {
          val id = row(0)
          val cmd = row(1)
          val timeout = row(2).toLong
          val pr = runCommand(cmd, timeout)
          statuses(index) = id -> (if (pr.timedOut) "ROK" else pr.code.toString)
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Uspesnih procesa: ${statuses.count(_._2 == "0")}")
    println(s"Neuspesnih procesa: ${statuses.count(s => s._2 != "0" && s._2 != "ROK")}")
    println(s"Istekao rok: ${statuses.count(_._2 == "ROK")}")
    println("Statusi: " + fmtPairs(statuses))
  }
}
