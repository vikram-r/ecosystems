import scala.util.Random

/**
  * Created by vikram on 8/1/16.
  */
object Console extends App {
  import Chromosome._

  import ListHelpers._
  def randomInput(size: Int, sum: Int): List[Int] = List.randomListWithSum(size, sum)

  val input: List[Int] = randomInput(size = 15, sum = 100)
  println(s"input: $input")

  //Hyperparameters //todo set these via command line

  //number of chromosomes per generation
  val NUM_CHROMOSOMES = 100

  //percent of chromosomes mutated per generation
  val MUTATION_RATE = .02f

  //percent of chromosomes preserved per generation
  val ELITISM_RATE = .01f

  //max number of evolution steps
  val MAX_EVOLUTIONS = 100

  //Threshold percentage of optimal fitness acceptable as result
  val ACCEPTABLE_THRESHOLD = .95f

  implicit val inputData = new MaxWeightedSumInputData(input, ACCEPTABLE_THRESHOLD)

  val ecosystem = new MaxWeightedSumEcosystem(
    numChromosomes = NUM_CHROMOSOMES,
    mutationRate = MUTATION_RATE,
    elitismRate = ELITISM_RATE
  )

  for {
    generation ← 0 until MAX_EVOLUTIONS
    mostFit = ecosystem.findAlphaOrganism()
    if !mostFit.exists(_.satisfiesThreshold())
  } {
    println(s"generation: $generation")
    println(mostFit.map(_.getData))
    println(s"highest fitness: ${mostFit.map(_.fitness + "").getOrElse("n/a")}")
    ecosystem.evolve()
  }

  val mostFit = ecosystem.findAlphaOrganism()
  println(mostFit.get.getData)

}
