
/**
  * This ecosystem solves the following:
  *
  * input: list of integers that sum 100
  *
  * output: list of integers that sum 100, and the dot product of the output with the input is maximized
  */
class MaxWeightedSumEcosystem(val numOrganisms: Int,
                              val crossoverRate: Float,
                              val mutationRate: Float,
                              val elitismRate: Float,
                              val threshold: Float,
                              val inputData: List[Int], //todo asdfsdaf
                              val maxSum: Int) extends Ecosystem[MaxWeightedSumOrganism] {

  /**
    * Randomly generate the appropriate number of organisms for an initial population
    *
    * @return the list of randomly generated organisms
    */
  override def initialPopulation: List[MaxWeightedSumOrganism] = {
    List.fill(numOrganisms)(MaxWeightedSumOrganism.newInstance(
      data = List.fill(inputData.size)(MaxWeightedSumOrganism.newBit(maxSum)),
      maxSum = maxSum,
      inputData = inputData
    ))
  }


}
