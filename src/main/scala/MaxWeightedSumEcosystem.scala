
//todo eventually do fitness calculations in parallel
/**
  * This ecosystem solves the following:
  *
  * input: list of integers that sum 100
  *
  * output: list of integers that sum 100, and the dot product of the output with the input is maximized
  */
class MaxWeightedSumEcosystem(val numChromosomes: Int,
                              val mutationRate: Float,
                              val elitismRate: Float)(implicit val inputData: MaxWeightedSumInputData) extends Ecosystem {

  def maxWeightedSumFitnessFunc(data: List[Int]): Float = {
    data.zip(inputData.data).map(e ⇒ e._1 * e._2).sum
  }

  /**
    * Randomly generate the appropriate number of chromosomes for an initial population
    *
    * @return the list of randomly generated chromosomes
    */
  def initialPopulation: List[Chromosome] = {
    import ListHelpers._
    val a = List.fill(numChromosomes)(Chromosome(List.randomListWithSum(inputData.size, inputData.maxSum), maxWeightedSumFitnessFunc))
    println(s"pop: ${a.map(_.getData)}")
    a
  }
}
