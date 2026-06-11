import java.io.File
import scala.io.Source
import scala.util.Try

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

  def processId(p: Process): String = {
    try p.getClass.getMethod("pid").invoke(p).toString
    catch { case _: Throwable => p.hashCode().toString }
  }

  def main(args: Array[String]): Unit = {
    val commands = nonEmptyLines("procesi.txt")
    commands.foreach { cmd =>
      val parts = splitCommand(cmd)
      if (parts.nonEmpty) {
        val p = new ProcessBuilder(parts: _*).redirectErrorStream(true).start()
        val pid = processId(p)
        p.waitFor()
        val out = Try(Source.fromInputStream(p.getInputStream).mkString.trim).getOrElse("")
        println(s"pid: $pid")
        println(s"komanda: $cmd")
        println(s"izlaz: $out")
      }
    }
  }
}
