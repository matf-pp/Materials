import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
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
      .cache()

    val total = games.count()
    val wins = games.filter(row => row(9).equalsIgnoreCase("W")).cache()
    val winCount = wins.count()
    val winPercent = if (total == 0) 0.0 else winCount * 100.0 / total
    val fullBoardWins = wins.filter(row => row.take(9).forall(cell => !cell.equalsIgnoreCase("b"))).count()
    val winsAfterMoreThanThreeMoves = wins.filter(row => row.take(9).count(_.equalsIgnoreCase("x")) > 3).count()

    val xCounts = games
      .flatMap(row => row.take(9).zipWithIndex.filter(_._1.equalsIgnoreCase("x")).map { case (_, idx) => (idx + 1, 1) })
      .reduceByKey(_ + _)
      .collectAsMap()
    val oCounts = games
      .flatMap(row => row.take(9).zipWithIndex.filter(_._1.equalsIgnoreCase("o")).map { case (_, idx) => (idx + 1, 1) })
      .reduceByKey(_ + _)
      .collectAsMap()

    sc.stop()

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
