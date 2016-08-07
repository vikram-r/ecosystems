
/**
  * Created by vikram on 8/1/16.
  */
object Console extends App {
  import ListHelpers._

  //Hyperparameters //todo set these via command line

  //number of chromosomes per generation
  val NUM_CHROMOSOMES = 100

  //percent of chromosomes that are allowed to mate per generation
  val CROSSOVER_RATE = .90f

  //percent of chromosomes mutated per generation
  val MUTATION_RATE = .02f

  //percent of chromosomes preserved per generation
  val ELITISM_RATE = .01f

  //max number of evolution steps
  val MAX_EVOLUTIONS = 10000

  //Threshold percentage of optimal fitness acceptable as result
  val ACCEPTABLE_THRESHOLD = .95f

  val weightedMaxSum = 100
  implicit val inputData = new MaxWeightedSumInputData(List.randomListWithSum(15, weightedMaxSum), weightedMaxSum, ACCEPTABLE_THRESHOLD)

  val ecosystem = new MaxWeightedSumEcosystem(
    numChromosomes = NUM_CHROMOSOMES,
    crossoverRate = CROSSOVER_RATE,
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
