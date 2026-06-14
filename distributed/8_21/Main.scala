// Komentar za studente: Spark transformacije grade RDD obradu, a akcije kao count, collect ili saveAsTextFile pokrecu izvrsavanje.
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  // Datoteka je dovoljno jednostavna da redove delimo po zarezima.
  def columns(line: String): Array[String] = line.split(",", -1).map(_.trim)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("IksOksStatistike")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val games = sc.textFile("tictactoe.csv")
      .filter(!_.startsWith("polje1,"))
      .map(columns)
      .filter(_.length >= 10)
      // Vise statistika koristi iste podatke, pa cache izbegava ponovno citanje datoteke.
      .cache()

    val total = games.count()
    // Kolona 9 cuva ishod; W znaci da je prvi igrac pobedio.
    val wins = games.filter(row => row(9).equalsIgnoreCase("W")).cache()
    val winCount = wins.count()
    val winPercent = if (total == 0) 0.0 else winCount * 100.0 / total
    // Puna tabla nema praznih polja, koja su ovde oznacena slovom b.
    val fullBoardWins = wins.filter(row => row.take(9).forall(cell => !cell.equalsIgnoreCase("b"))).count()
    // Vise od tri oznake x znaci da je prvi igrac odigrao bar cetiri poteza.
    val winsAfterMoreThanThreeMoves = wins.filter(row => row.take(9).count(_.equalsIgnoreCase("x")) > 3).count()

    // Brojimo koliko puta je prvi igrac koristio svako polje.
    val xCounts = games
      .flatMap(row => row.take(9).zipWithIndex.filter(_._1.equalsIgnoreCase("x")).map { case (_, idx) => (idx + 1, 1) })
      .reduceByKey(_ + _)
      .collectAsMap()
    // Brojimo koliko puta je drugi igrac koristio svako polje.
    val oCounts = games
      .flatMap(row => row.take(9).zipWithIndex.filter(_._1.equalsIgnoreCase("o")).map { case (_, idx) => (idx + 1, 1) })
      .reduceByKey(_ + _)
      .collectAsMap()

    sc.stop()

    // Posle collectAsMap ove male mape su obicne lokalne Scala kolekcije.
    val favoriteFirst = (1 to 9).maxBy(field => xCounts.getOrElse(field, 0))
    val leastFavoriteSecond = (1 to 9).minBy(field => oCounts.getOrElse(field, 0))
    val output = Seq(
      f"Procenat pobeda prvog igraca: $winPercent%.2f%%",
      s"Pobede prvog igraca sa punom tablom: $fullBoardWins",
      s"Pobede prvog igraca u vise od tri poteza: $winsAfterMoreThanThreeMoves",
      s"Najomiljenije polje prvog igraca: $favoriteFirst",
      s"Najmanje omiljeno polje drugog igraca: $leastFavoriteSecond"
    )
    println(output.mkString("\n"))
  }
}
