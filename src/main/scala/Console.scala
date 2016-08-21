
/**
  * Created by vikram on 8/1/16.
  */
object Console extends App {
  import ListHelpers._

  //Hyperparameters //todo set these via command line

  //number of organisms per generation
  val NUM_ORGANISMS = 100

  //percent of organisms that are allowed to mate per generation
  val CROSSOVER_RATE = .80f

  //percent chance of a organisms to mutate per generation
  val MUTATION_RATE = .05f

  //percent of organisms preserved per generation
  val ELITISM_RATE = .00f

  //max number of evolution steps
  val MAX_EVOLUTIONS = 10000

  //Threshold percentage of optimal fitness acceptable as result
  val ACCEPTABLE_THRESHOLD = 1f

  val weightedMaxSum = 100
  implicit val inputData = new MaxWeightedSumInputData(List.randomListWithSum(15, weightedMaxSum), weightedMaxSum, ACCEPTABLE_THRESHOLD)

  val ecosystem = new MaxWeightedSumEcosystem(
    numOrganisms = NUM_ORGANISMS,
    crossoverRate = CROSSOVER_RATE,
    mutationRate = MUTATION_RATE,
    elitismRate = ELITISM_RATE
  )

  val result = ecosystem.start(MAX_EVOLUTIONS)

  println("**************************************")
  println(s"Optimal Fitness: ${inputData.optimalFitness}")
  println(s"Input data: ${inputData.inputData}")
  println(s"Most fit  : ${result.alphaOrganism.data}")

  println(s"Result: $result")
}
