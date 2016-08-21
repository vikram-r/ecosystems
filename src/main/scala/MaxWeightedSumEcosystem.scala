
//todo eventually do fitness calculations in parallel
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
                              val elitismRate: Float)(implicit val inputData: MaxWeightedSumInputData) extends Ecosystem {

  /**
    * Randomly generate the appropriate number of organisms for an initial population
    *
    * @return the list of randomly generated organisms
    */
  override def initialPopulation: List[Organism] = {
    List.fill(numOrganisms)(Organism(List.fill(inputData.size)(inputData.newOrganismDataElem()), inputData.fitnessFunc))
  }
}
