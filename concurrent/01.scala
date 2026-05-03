import scala.io.Source
import java.util.concurrent.atomic.AtomicLong

object RestaurantRevenue {
  def main(args: Array[String]): Unit = {
    val numThreads = scala.io.StdIn.readInt()

    val lines = Source.fromFile("porudzbine.txt").getLines().toArray
    val totalLines = lines.length

    val totalRevenue = new AtomicLong(0)

    val chunkSize = (totalLines + numThreads - 1) / numThreads

    val threads = (0 until numThreads).map { i =>
      new Thread(() => {
        val start = i * chunkSize
        val end = math.min(start + chunkSize, totalLines)

        var localSum = 0L

        for (j <- start until end) {
          val parts = lines(j).split(" ")
          val price = parts(1).toLong
          localSum += price
        }

        totalRevenue.addAndGet(localSum)
      })
    }

    threads.foreach(_.start())
    threads.foreach(_.join())

    println(s"Ukupan prihod: ${totalRevenue.get()}")
  }
}
