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

  def main(args: Array[String]): Unit = {
    val commands = nonEmptyLines("procesi_status.txt")
    val codes = new Array[Int](commands.length)
    // Svaka nit pokrece jednu proveru i upisuje izlazni kod na svoj indeks.
    val threads = commands.zipWithIndex.map { case (command, index) =>
      new Thread(new Runnable {
        def run(): Unit = {
          codes(index) = runCommand(command).code
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    println(s"Uspesnih procesa: ${codes.count(_ == 0)}")
    println(s"Neuspesnih procesa: ${codes.count(_ != 0)}")
    println("Statusi: " + codes.zipWithIndex.map { case (c, i) => s"${i + 1}=$c" }.mkString(", "))
  }
}
