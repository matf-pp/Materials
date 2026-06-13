import java.io.File
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {
  def deleteRecursively(file: File): Unit = {
    if (file.exists()) {
      if (file.isDirectory) file.listFiles().foreach(deleteRecursively)
      file.delete()
    }
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ApacheHadoopPaketi")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)

    val packageSizes = sc.wholeTextFiles("mavenLog.txt")
      .flatMap { case (_, text) =>
        val lines = text.split("\\r?\\n").toSeq
        lines.sliding(2).collect {
          case Seq(downloaded, sizeLine)
              if downloaded.startsWith("Downloaded:") &&
                 downloaded.toLowerCase.contains("hadoop") =>
            val packageName = downloaded.trim.split("/").last.trim
            val size = sizeLine.trim
              .stripPrefix("(")
              .split("\\s+at\\s+")
              .headOption
              .getOrElse("")
              .trim
            s"$packageName: $size"
        }
      }

    deleteRecursively(new File("ApacheDownloaded"))
    packageSizes.saveAsTextFile("ApacheDownloaded")
    sc.stop()
  }
}
