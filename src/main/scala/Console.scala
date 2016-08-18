
/**
  * Created by vikram on 8/1/16.
  */
object Console extends App {
  import ListHelpers._

  //Hyperparameters //todo set these via command line

  //number of chromosomes per generation
  val NUM_CHROMOSOMES = 100

  //percent of chromosomes that are allowed to mate per generation
  val CROSSOVER_RATE = .80f

  //percent chance of a chromosomes to mutate per generation
  val MUTATION_RATE = .05f

  //percent of chromosomes preserved per generation
  val ELITISM_RATE = .00f

  //max number of evolution steps
  val MAX_EVOLUTIONS = 10000

  //Threshold percentage of optimal fitness acceptable as result
  val ACCEPTABLE_THRESHOLD = 1f

  val weightedMaxSum = 100
  implicit val inputData = new MaxWeightedSumInputData(List.randomListWithSum(15, weightedMaxSum), weightedMaxSum, ACCEPTABLE_THRESHOLD)

  val ecosystem = new MaxWeightedSumEcosystem(
    numChromosomes = NUM_CHROMOSOMES,
    crossoverRate = CROSSOVER_RATE,
    mutationRate = MUTATION_RATE,
    elitismRate = ELITISM_RATE
  )

  var continue = true //todo gross, this should be refactored
  for {
    generation ← 0 until MAX_EVOLUTIONS
    if continue
  } {
    println(s"generation: $generation")
    ecosystem.evolve()

    val mostFit = ecosystem.findAlphaOrganism()
    println(mostFit.map(_.data))
    println(s"highest fitness: ${mostFit.map(_.fitness + "").getOrElse("n/a")}")

    if (mostFit.exists(_.satisfiesThreshold())) {
      println("threshold met!")
      continue = false
    }

  }
  println("**************************************")
  println(s"Optimal Fitness: ${inputData.optimalFitness}")
  println(s"Input data: ${inputData.data}")

  val mostFit = ecosystem.findAlphaOrganism()
  println(s"Most fit  : ${mostFit.get.data}")

}
