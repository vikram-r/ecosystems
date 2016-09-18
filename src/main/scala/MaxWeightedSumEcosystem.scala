
/**
  * This ecosystem solves the following:
  *
  * input: list of integers
  *
  * output: list of integers that match the following conditions
  *  - same size as input
  *  - sum of all elements is maxSum
  *  - dot product of the output with the input is maximized
  *
  *  This is a trivial problem, as the optimal solution is a list of the form [0, 0, 0, maxSum, 0]
  *  where the index of the only non-zero value is the index that contains the maximum value in the
  *  input list.
  */
class MaxWeightedSumEcosystem(val inputData: List[Int],
                              val maxSum: Int,
                              val numOrganisms: Int,
                              val crossoverRate: Float,
                              val mutationRate: Float,
                              val elitismRate: Float,
                              val threshold: Float) extends Ecosystem[MaxWeightedSumOrganism] {

  /**
    * Randomly generate the appropriate number of MaxWeightedSumOrganisms for an initial population
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

  /**
    * Basic logging
    * @param result the result from running the Ecosystem
    */
  override def handleResult(result: ResultData): Unit = {
    println("**************************************")
    println(s"Optimal Fitness: ${result.alphaOrganism.optimalFitness}")
    println(s"Highest Fitness: ${result.alphaOrganism.fitness}")
    println(s"Input data   : $inputData")
    println(s"Most fit data: ${result.alphaOrganism}")
    println(s"Result: $result")
  }
}
