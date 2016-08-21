
//todo eventually do fitness calculations in parallel
/**
  * This ecosystem solves the following:
  *
  * input: list of integers that sum 100
  *
  * output: list of integers that sum 100, and the dot product of the output with the input is maximized
  */
class MaxWeightedSumEcosystem(val numChromosomes: Int,
                              val crossoverRate: Float,
                              val mutationRate: Float,
                              val elitismRate: Float)(implicit val inputData: MaxWeightedSumInputData) extends Ecosystem {

  /**
    * Randomly generate the appropriate number of chromosomes for an initial population
    *
    * @return the list of randomly generated chromosomes
    */
  override def initialPopulation: List[Chromosome] = {
    //todo this is simplified so output data does not have to sum maxSum. Should be changed once crossover is refactored to only generate such that the constraint matches
    List.fill(numChromosomes)(Chromosome(List.fill(inputData.size)(inputData.newChromosomeDataElem()), inputData.fitnessFunc))
  }
}
