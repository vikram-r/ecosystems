import Chromosome._

//todo eventually do fitness calculations in parallel
/**
  * Habitat for Chromosomes, used to simulate each evolution step
  */
class MaxWeightedSumEcosystem(val numChromosomes: Int,
                              val mutationRate: Float,
                              val elitismRate: Float)(implicit val inputData: InputData) extends Ecosystem {

  def maxWeightedSumFitnessFunc(data: List[Int]): Float = {
    val b = data.zip(inputData.data).map(e ⇒ e._1 * e._2).sum
    println(s"calculated fitness: $b")
    b
  }

  /**
    * Randomly generate the appropriate number of chromosomes for an initial population
    *
    * @return the list of randomly generated chromosomes
    */
  def initialPopulation: List[Chromosome] = {
    val a = List.fill(numChromosomes)(Chromosome.apply(inputData.size, maxWeightedSumFitnessFunc))
    println(s"pop: ${a.map(_.getData)}")
    a
  }
}
