
/**
  * Created by vikram on 8/1/16.
  */
object Console extends App {
  import ListHelpers._

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

//  val ecosystem = new MaxWeightedSumEcosystem(
//    inputData = List.randomListWithSum(15, weightedMaxSum),
//    numOrganisms = NUM_ORGANISMS,
//    crossoverRate = CROSSOVER_RATE,
//    mutationRate = MUTATION_RATE,
//    elitismRate = ELITISM_RATE,
//    threshold = ACCEPTABLE_THRESHOLD,
//    maxSum = weightedMaxSum
//  )

  val ecosystem = new KnapsackEcosystem(
    allItems = List.fill(15)(KnapsackItem.randomItem(50, 100)),
    capacity = 300,
    numOrganisms = NUM_ORGANISMS,
    crossoverRate = CROSSOVER_RATE,
    mutationRate = MUTATION_RATE,
    elitismRate = ELITISM_RATE,
    threshold = ACCEPTABLE_THRESHOLD
  )

  ecosystem.run(MAX_EVOLUTIONS)
}
