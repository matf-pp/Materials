object Main {
  val MOD = 1000000007L
  val text = "Zdravo"

  def hash(s: String, h: Long): Long = {
    var result = h

    for (c <- s) {
      result = (result * 31 + c.toInt) % MOD
    }

    result
  }

  class HashThread(val index: Int, val results: Array[Long])
      extends Thread {

    override def run(): Unit = {
      var h = (index + 1).toLong
      val repetitions = 1000000L + index.toLong * 100000L

      var j = 0L
      while (j < repetitions) {
        h = hash(text, h)
        j += 1
      }

      results(index) = h
    }
  }

  def main(args: Array[String]): Unit = {
    val n = scala.io.StdIn.readInt()

    val results = new Array[Long](n)
    val threads = new Array[HashThread](n)

    for (i <- 0 until n) {
      threads(i) = new HashThread(i, results)
      threads(i).start()
    }

    for (i <- 0 until n) {
      threads(i).join()
    }

    for (i <- 0 until n) {
      println(s"${i} = ${results(i)}")
    }
  }
}

