import java.io.File
import scala.io.Source

object Main {
  def readLines(file: String): List[String] = {
    val f = new File(file)
    if (!f.exists()) Nil else {
      val src = Source.fromFile(f)
      try src.getLines().toList finally src.close()
    }
  }

  def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  def main(args: Array[String]): Unit = {
    val workers = stdinTokens().headOption.map(_.toInt).getOrElse(1).max(1)
    val lines = readLines("dnk.biodata")
    val local = Array.fill(workers, 4)(0)
    // Linije DNK zapisa se dele nitima; svaka nit popunjava svoj lokalni brojac.
    val threads = (0 until workers).map { index =>
      new Thread(new Runnable {
        def run(): Unit = {
          val from = index * lines.length / workers
          val until = (index + 1) * lines.length / workers
          lines.slice(from, until).mkString.toUpperCase.foreach {
            case 'A' => local(index)(0) += 1
            case 'G' => local(index)(1) += 1
            case 'C' => local(index)(2) += 1
            case 'T' => local(index)(3) += 1
            case _ =>
          }
        }
      })
    }
    threads.foreach(_.start())
    threads.foreach(_.join())
    val counts = local.foldLeft(Array.fill(4)(0)) { (acc, part) =>
      for (i <- acc.indices) acc(i) += part(i)
      acc
    }
    println(s"A=${counts(0)}, G=${counts(1)}, C=${counts(2)}, T=${counts(3)}")
  }
}
