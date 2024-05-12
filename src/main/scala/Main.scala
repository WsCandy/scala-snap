import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {
    println("Hello!")
    println("Here's an example of the simplest way of reading user input:")
    val yourInput: String = StdIn.readLine()
    println(s"You entered $yourInput.")
  }
}
