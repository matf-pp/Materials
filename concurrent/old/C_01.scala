/* Sto se tice problema 3-bojenja grafa, Ivan primenjuje drugaciju taktiku od Milana.
   Generisao je sva moguca 3 bojenja grafa sa sedam cvorova (3-bojenja grafa) i sada ce koristeci
   Scala niti proparsirati ona validna.

   Napisati program koji od svih 3-bojenja grafa sa sedam cvorova koja se nalaze u datoteci ivans_coloring.data
   filtrira i na standardni izlaz ispisuje ona validna.
   Programu se sa standardnog ulaza unosi broj niti, program zatim tim nitima priblizno ravnomerno deli posao,
   nakon cega svaka od njih filtrira njoj dodeljena bojenja, pri cemu sve niti validna resenja dodaju u zajednicki konkurentni set.

   Nakon zavrsetka rada svih niti, glavna nit na standardni izlaz ispisuje validna bojenja koja su sacuvana u skupu.

   Korisitit klasu ConcurrentLHashMap i njen metod .newKeySet().
 */
package concurrent

import java.util.concurrent._
import java.util.Scanner
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.mutable.ArrayBuffer

class Brojac(poc : Int, kraj : Int,
            linije : ArrayBuffer[Array[Int]],
             brojac : AtomicInteger)
  extends Thread {

  override def run() : Unit = {
  println("Start coloring search..")
  for(i <- poc until kraj){
      val col = linije(i)
      if(col(0)!=col(1) && col(0)!=col(3) && col(1)!=col(2) && col(1)!=col(3) && col(2)!=col(3) && col(2)!=col(4) && col(3)!=col(4) && col(3)!=col(5) && col(4)!=col(5) && col(6)==0 && col(2)!=1){
        brojac.incrementAndGet()
      }
    }
    println("End coloring search..")
  }
}

object GraphColoring {
  def main(args : Array[String]) {
    val sc1 : Scanner = new Scanner(new File("ivans_colorings.data"))
    val sc2 : Scanner = new Scanner(System.in)

    println("Unesite broj niti: ")
    println("Broj procesora na racunaru koji su na raspolaganju je : " + Runtime.getRuntime.availableProcessors())

    val brojNiti = sc2.nextInt()

    val brojaci = new Array[Brojac](brojNiti)
    val linije = new ArrayBuffer[Array[Int]]()

    while(sc1.hasNextLine){
      val col = sc1.nextLine()
      val list = col.substring(1, col.length - 1).split(",\\s*").map(_.toInt)
      linije.append(list)
    }

    val brojLinija = linije.length
    println(brojLinija)

    val total = new AtomicInteger()
    val korak = Math.ceil(brojLinija.toDouble/brojNiti.toDouble).toInt
    for(i <- 0 until brojNiti)
      brojaci(i) = new Brojac(i*korak, Math.min((i+1)*korak, brojLinija), linije, total)

    for(b <- brojaci)
      b.start()

    for(b <- brojaci)
      b.join()

    println("Broj: " + total)
  }
}
