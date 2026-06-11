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

  def csv(line: String): Array[String] = line.split(",", -1).map(_.trim)
  def pipe(line: String): Array[String] = line.split("\\|", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val rows = nonEmptyLines("komande_retry.csv").map(pipe)
    var successes = 0
    var failures = 0
    var attemptsTotal = 0
    rows.foreach { r =>
      val maxAttempts = r(2).toInt
      var attempt = 0
      var okDone = false
      while (attempt < maxAttempts && !okDone) {
        attempt += 1
        val pr = runCommand(r(1))
        if (pr.code == 0) okDone = true
      }
      attemptsTotal += attempt
      if (okDone) successes += 1 else failures += 1
    }
    println(s"Komandi: ${rows.length}")
    println(s"Uspesno: $successes")
    println(s"Neuspesno: $failures")
    println(s"Pokusaja ukupno: $attemptsTotal")
  }
}
