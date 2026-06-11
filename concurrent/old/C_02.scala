package Zadatak1

import java.io.File
import java.util.Scanner
import java.util.concurrent._


class Ucesnik (ime : String, dobitnici: Array[(String, String)]) extends Thread{

  override def run(): Unit = {
    dobitnici.synchronized {
      dobitnici.wait()
    }

    for (d <- dobitnici) {
      if (d._1 == ime.split(" ")(0)) {
        println("Student: " + ime.split(" ")(1) + " " + ime.split(" ")(2) + " je dobitnik nagrade: " + d._2)
      }
    }
  }

  def getIme: String ={
    return  ime
  }

}

object Prvi {
  def main(args: Array[String]): Unit = {
    val sc: Scanner = new Scanner(new File("studenti.info"))
    val nagr1 = 1
    val nagr2 = 3
    val nagr3 = 5
    val m = nagr1 + nagr2 + nagr3

    // Create winners
    val dobitnici = new Array[(String, String)](m)

    // Parse students
    val n = sc.nextInt()
    println("Broj ucesnika: "+n)
    sc.nextLine()
    val ucesnici = new Array[Ucesnik](n)
    var i = 0
    while (sc.hasNext) {
      val line = sc.nextLine()
      ucesnici(i) = new Ucesnik(line, dobitnici)
      i += 1
    }

    // Start students
    for (u <- ucesnici) {
      u.start()
    }

    dobitnici.synchronized {
      // Choose lucky ones
      val izvuceni = ThreadLocalRandom.current().ints(0, n)
        .distinct().limit(m).toArray

      // 1st awars
      for (j <- 0 until nagr1) {
        dobitnici(j) = (ucesnici(izvuceni(j)).getIme.split(" ")(0), "Kopaonik 10 dana za 2 osobe")
      }

      // 2nd award
      for (j <- nagr1 until nagr1+nagr2) {
        dobitnici(j) = (ucesnici(izvuceni(j)).getIme.split(" ")(0), "Srebrno jezero 7 dana za 2 osobe")
      }

      // 3rd one
      for (j <- nagr1+nagr2 until izvuceni.length) {
        dobitnici(j) = (ucesnici(izvuceni(j)).getIme.split(" ")(0), "Vikend na Tari za 3 osobe")
      }

      // Notify winners
      dobitnici.notifyAll()
    }
  }
}