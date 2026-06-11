import java.io.{File, PrintWriter}
import scala.io.Source

object Main {
  private def stdinTokens(): Array[String] =
    Source.stdin.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray

  private def fileTokens(path: String): Array[String] = {
    val source = Source.fromFile(path)
    try source.getLines().flatMap(_.trim.split("\\s+").filter(_.nonEmpty)).toArray
    finally source.close()
  }

  private def writeMatrix(path: String, matrix: Array[Array[Long]]): Unit = {
    val writer = new PrintWriter(new File(path))
    try matrix.foreach(row => writer.println(row.mkString(" ")))
    finally writer.close()
  }

  def main(args: Array[String]): Unit = {
    val threadCount = stdinTokens().headOption.map(_.toInt).getOrElse(1) max 1
    val tokens = fileTokens("matrica.txt")
    val rows = tokens(0).toInt
    val cols = tokens(1).toInt
    val data = tokens.drop(2).map(_.toLong)
    val matrix = Array.tabulate(rows, cols)((i, j) => data(i * cols + j))
    val transposed = Array.ofDim[Long](cols, rows)

    // Svaka nit transponuje disjunktan skup vrsta ulazne matrice.
    val threads = (0 until threadCount).map { index =>
      new Thread(new Runnable {
        override def run(): Unit = {
          val from = index * rows / threadCount
          val until = (index + 1) * rows / threadCount
          for {
            i <- from until until
            j <- 0 until cols
          } transposed(j)(i) = matrix(i)(j)
        }
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    writeMatrix("matrica_t.txt", transposed)
  }
}
