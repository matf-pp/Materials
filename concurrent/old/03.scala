import java.util.concurrent.Executors
import scala.io.Source
import scala.util.Random
import scala.collection.mutable

object DCLCacheApp {

  def main(args: Array[String]): Unit = {

    val nThreads = scala.io.StdIn.readInt()

    val products: Map[Int, String] =
      Source.fromFile("proizvodi.csv")
        .getLines()
        .drop(1)
        .map { line =>
          val Array(id, name) = line.split(",")
          id.toInt -> name
        }
        .toMap

    val ids = products.keys.toList

    val cache = mutable.Map[Int, String]()
    val lock = new Object

    val pool = Executors.newFixedThreadPool(nThreads)

    for (t <- 1 to nThreads) {
      pool.execute(new Runnable {
        override def run(): Unit = {

          val shuffled = Random.shuffle(ids)

          for (id <- shuffled) {

            var value: String = null
            var isHit = false

            // 1. PRVI CHECK (bez lock-a)
            cache.synchronized {
              if (cache.contains(id)) {
                value = cache(id)
                isHit = true
              }
            }

            if (!isHit) {

              // 2. SYNCHRONIZED BLOK (DCL)
              lock.synchronized {

                // DRUGI CHECK
                if (cache.contains(id)) {
                  value = cache(id)
                  isHit = true
                } else {
                  value = products(id)
                  cache(id) = value
                  isHit = false
                }
              }
            }

            val status = if (isHit) "HIT " else "MISS"
            println(s"Nit $t: $status $id -> $value")
          }
        }
      })
    }

    pool.shutdown()
    while (!pool.isTerminated) {
      Thread.sleep(50)
    }
  }
}
