import java.io.File
import java.util.Scanner
import java.util.concurrent.ConcurrentLinkedQueue

class Nit(putanja : String,
          rezultat : ConcurrentLinkedQueue[String],
          odrediste : String,
          naziv : String) extends Thread {

  override def run(): Unit = {
    val sc: Scanner = new Scanner(new File(putanja))
    while (sc.hasNextLine()) {
      val odr: String = sc.next()
      val dana : Int = sc.nextInt()
      val cena: Int = sc.nextInt()

      if (odr.compareTo(odrediste) == 0) {
        rezultat.add(naziv + " " + dana + " " + cena)
      }
    }
  }
}

object Travel {
  def main(args: Array[String]): Unit = {
    // Load destination
    println("Insert destination: ")
    val sc : Scanner = new Scanner(System.in)
    val odrediste : String = sc.nextLine()

    // Load offers
    val rezultat = new ConcurrentLinkedQueue[String]()

    val niti = new Array[Nit](3)
    niti(0) = new Nit("ITravel.data", rezultat, odrediste, "ITravel")
    niti(1) = new Nit("LignjaTravel.data", rezultat, odrediste, "LignjasTravel")
    niti(2) = new Nit("Travellove.data", rezultat, odrediste, "Travellove")

    for(nit <- niti)
      nit.start()

    for(nit <- niti)
      nit.join()

    // Writeout results
    if(rezultat.isEmpty){
      println("Nema ponuda za zadato odrediste: "+odrediste)
    }else {
      println("Pristigle ponude za odrediste: "+odrediste)
      println(rezultat.toString)
      println("Najbolja ponuda (cena/dan): ")
      var min_ponuda: String = ""
      var min_data : Float = -1
      rezultat.forEach(elem => {
        var tmpdan : Float = elem.split(" ")(1).toFloat
        var tmpcena : Float = elem.split(" ")(2).toFloat
        var tmp_data : Float = tmpcena / tmpdan
        if (min_data<0 || tmp_data<min_data){
          min_data = tmp_data
          min_ponuda = elem
        }
      })
      println(min_ponuda)
    }
  }
}
