 package konkurentno
import java.util.concurrent._
import java.util.Scanner
import java.io.File

import scala.collection.mutable.ArrayBuffer

class Brojac(poc : Int, kraj : Int,
             linije : ArrayBuffer[String],
             mapaKaraktera : ConcurrentHashMap[Char, Int])
    extends Thread {

    override def run() {
        val karakteri = new Array[Int](26)
        for (c <- 'a' to 'z')
            karakteri(c - 'a') = 0

        for(i <- poc until kraj) {
            for (c <- linije(i).toLowerCase)
                if (c >= 'a' && c <= 'z')
                    karakteri(c - 'a') = karakteri(c - 'a') + 1
        }

        mapaKaraktera.synchronized {
            for (i <- 0 until karakteri.length) {
                val c : Char = ('a' + i).toChar;
                mapaKaraktera.replace(c, mapaKaraktera.get(c) + karakteri(i))
            }
        }
    }
}

object Zadatak1Main {
    def main(args : Array[String]) {
        val sc1 : Scanner = new Scanner(new File("lorem.txt"))

        println("Unesite broj niti: ")
        val brojNiti = scala.io.StdIn.readInt()

        val linije = new ArrayBuffer[String]()
        while(sc1.hasNextLine)
            linije.append(sc1.nextLine())

        val mapaKaraktera = new ConcurrentHashMap[Char, Int](4,4,brojNiti)
        for (c <- 'a' to 'z')
            mapaKaraktera.put(c, 0)

        val brojLinija = linije.length
        val korak = Math.ceil(brojLinija.toDouble/brojNiti.toDouble).toInt

        val brojaci = new Array[Brojac](brojNiti)
        for(i <- 0 until brojNiti)
            brojaci(i) = new Brojac(i*korak, Math.min((i+1)*korak, brojLinija), linije, mapaKaraktera)

        for(b <- brojaci)
            b.start()

        for(b <- brojaci)
            b.join()

        println("Rezultati konkurentnog izvrsavanja")
        println(mapaKaraktera.toString)
    }
}

