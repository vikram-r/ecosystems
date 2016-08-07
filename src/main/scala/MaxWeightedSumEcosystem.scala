import scala.util.Random

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

  def maxWeightedSumFitnessFunc(data: List[Int]): Float = {
    data.zip(inputData.data).map(e ⇒ e._1 * e._2).sum
  }

  /**
    * Randomly generate the appropriate number of chromosomes for an initial population
    *
    * @return the list of randomly generated chromosomes
    */
  override def initialPopulation: List[Chromosome] = {
    import ListHelpers._
    val a = List.fill(numChromosomes)(Chromosome(List.randomListWithSum(inputData.size, inputData.maxSum), maxWeightedSumFitnessFunc))
    println(s"pop: ${a.map(_.getData)}")
    a
  }


  override def evolve() = {
    println("evolving!")

    val elitismNum = (elitismRate * numChromosomes).toInt
    val crossoverNum = (crossoverRate * numChromosomes).toInt
    val mutationNum = (mutationRate * numChromosomes).toInt

    val sorted = organisms.sortBy(_.fitness).reverse
    val breedingPool = sorted.slice(elitismNum, elitismNum + crossoverNum)

    //iterator to generate new children
    val c = Iterator.continually {
      //randomly select 2 chromosomes from the breeding pool to mate
      Chromosome.mate(
        breedingPool(Random.nextInt(breedingPool.size)),
        breedingPool(Random.nextInt(breedingPool.size))
      )
    }

    val newGeneration = sorted.take(elitismNum) ++ c.take(sorted.size - elitismNum)

    //todo mutation

    organisms = newGeneration
    numEvolutions += 1
  }
}
