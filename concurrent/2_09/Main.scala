import java.io.File
import java.util.concurrent.CyclicBarrier
import scala.io.Source

class Worker(
    val id: Int,
    val data: List[Int],
    val firstBarrier: CyclicBarrier,
    val secondBarrier: CyclicBarrier,
    val sumLock: Object,
    val totalSum: Array[Int]
) extends Thread {

  var localSum = 0

  override def run(): Unit = {
    // =========================
    // PRVA FAZA
    // =========================

    localSum = data.sum

    // Zasticen upis u ukupnu sumu
    sumLock.synchronized {
      totalSum(0) += localSum
    }

    // Cekanje da svi radnici zavrse prvu fazu
    firstBarrier.await()

    // =========================
    // DRUGA FAZA
    // =========================

    println(s"Radnik ${id}: lokalni zbir = ${localSum}")

    // Cekanje da svi radnici zavrse drugu fazu
    secondBarrier.await()
  }
}

object Main {

  def readLines(file: String): List[String] = {
    val f = new File(file)

    if (!f.exists()) Nil
    else {
      val src = Source.fromFile(f)
      try src.getLines().toList
      finally src.close()
    }
  }

  def nonEmptyLines(file: String): List[String] =
    readLines(file).map(_.trim).filter(_.nonEmpty)

  def stdinLines(): List[String] =
    Source.stdin.getLines().toList

  def stdinTokens(): Array[String] =
    stdinLines()
      .flatMap(_.trim.split("\\s+").filter(_.nonEmpty))
      .toArray

  def main(args: Array[String]): Unit = {

    val workers = stdinTokens()(0).toInt
    val nums = nonEmptyLines("delovi.txt").map(_.toInt)

    // Deljena ukupna suma
    // Koristimo niz kako bi Worker mogao da je menja.
    val totalSum = Array(0)

    // Zakljucavanje pristupa ukupnoj sumi
    val sumLock = new Object

    // workers radnika + glavna nit
    val firstBarrier = new CyclicBarrier(
      workers + 1,
      new Runnable {
        override def run(): Unit =
          println("Prva faza je gotova")
      }
    )

    // Barijera za kraj druge faze
    val secondBarrier = new CyclicBarrier(workers + 1)

    // Velicina bloka
    val chunk =
      Math.ceil(nums.length.toDouble / workers).toInt.max(1)

    // Kreiranje radnika
    val threads = (0 until workers).map { i =>
      val from = i * chunk
      val until = Math.min((i + 1) * chunk, nums.length)

      val block = nums.slice(from, until)

      new Worker(
        i + 1,
        block,
        firstBarrier,
        secondBarrier,
        sumLock,
        totalSum
      )
    }

    // Pokretanje svih radnika
    threads.foreach(_.start())

    // Glavna nit ceka da svi radnici zavrse prvu fazu
    firstBarrier.await()

    // Glavna nit odobrava prelazak u drugu fazu
    // time sto ucestvuje u barijeri.
    //
    // Radnici su vec oslobodjeni iz firstBarrier-a,
    // pa ovde samo cekamo da svi zavrse drugu fazu.
    secondBarrier.await()

    // Svi radnici su zavrsili obe faze
    println(s"Konačna suma = ${totalSum(0)}")

    // Cekamo da se niti zavrse
    threads.foreach(_.join())
  }
}

