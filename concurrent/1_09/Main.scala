import scala.io.Source
import java.io.File

object Main {

  /*
   * A thread responsible for checking one file.
   *
   * The result is written to results[index]:
   *   -1  -> the file does not exist
   *   >=0 -> number of characters in the file
   */
  class FileCheckThread(
      val path: String,
      val results: Array[Int],
      val index: Int
  ) extends Thread {

    override def run(): Unit = {
      val file = new File(path)
      if (!file.exists()) {
        results(index) = -1
      } else {
        val source = Source.fromFile(file)
        try {
          val content = source.mkString
          results(index) = content.length
        } finally {
          source.close()
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {

    val paths = Source
      .fromFile("datoteke_provere.txt")
      .getLines()
      .toArray

    val maxThreads = scala.io.StdIn.readInt()
    val results = new Array[Int](paths.length)
    val threads = new Array[FileCheckThread](paths.length)

    /*
     * active contains the threads that have been started
     * but have not yet been collected by the main thread.
     */
    var active = List.empty[FileCheckThread]

    /*
     * Index of the next file for which a thread should be
     * created.
     */
    var next = 0

    /*
     * Continue until every file has been assigned to a thread
     * and every created thread has finished.
     */
    while (next < paths.length || active.nonEmpty) {

      /*
       * Start new threads while there are files remaining
       * and we have not reached the maximum number of
       * simultaneously active threads.
       */
      while (next < paths.length && active.size < maxThreads) {

        val thread = new FileCheckThread(
          paths(next),
          results,
          next
        )

        threads(next) = thread

        /*
         * Add the thread to the list of active workers before
         * starting it.
         */
        active = thread :: active

        thread.start()

        next += 1
      }

      /*
       * At this point either:
       *
       *   1. all files have been assigned, or
       *   2. maxThreads workers are active.
       *
       * If there are active workers, wait until one of them
       * finishes before starting another worker.
       *
       * isAlive tells us whether the thread is still running.
       */
      val finished = active.find(!_.isAlive)

      finished match {

        case Some(thread) =>
          /*
           * The thread has already finished. Calling join()
           * ensures that it has completely terminated before
           * removing it from the active list.
           */
          thread.join()

          /*
           * Remove the finished thread from the active list.
           * This creates a free slot for another file.
           */
          active = active.filter(_ != thread)

        case None =>
          /*
           * No active thread has finished yet.
           *
           * Instead of repeatedly checking the threads in a
           * tight loop, wait for one of them to finish.
           *
           * The first thread in the list is chosen here.
           */
          val thread = active.head

          thread.join()

          active = active.tail
      }
    }

    /*
     * All files have now been processed.
     *
     * Calculate the requested statistics from the results.
     */
    var existing = 0
    var missing = 0
    var nonEmpty = 0
    var totalCharacters = 0

    for (length <- results) {
      if (length == -1) {
        missing += 1
      } else {
        existing += 1
        totalCharacters += length

        if (length > 0) {
          nonEmpty += 1
        }
      }
    }

    /*
     * Print the final report.
     */
    println(s"Postojecih datoteka: $existing")
    println(s"Nedostajucih datoteka: $missing")
    println(s"Nepraznih datoteka: $nonEmpty")
    println(s"Ukupno znakova: $totalCharacters")
  }
}

