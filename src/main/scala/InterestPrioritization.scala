/**
  * Created by vikram on 8/1/16.
  */
object InterestPrioritization extends App {
  import Chromosome._

  println("hello, cool App can be used as a cleaner entry point!")

  val input = List(List(1, 2, 3), List(4, 5, 3), List(6, 7, 8))

  implicit val IDEAL_FITNESS = IdealFitness(input)

  val c = Chromosome(3)


}
