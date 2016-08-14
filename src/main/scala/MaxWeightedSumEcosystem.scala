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
    //todo this is simplified so output data does not have to sum maxSum. Should be changed once crossover is refactored to only generate such that the constraint matches
    List.fill(numChromosomes)(Chromosome(List.fill(inputData.size)(inputData.newChromosomeDataElem()), maxWeightedSumFitnessFunc))
  }


  override def evolve() = {
    println(s"printing generation $numEvolutions")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println(organisms.mkString("\n"))
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

    val elitismNum = (elitismRate * numChromosomes).toInt
    val crossoverNum = (crossoverRate * numChromosomes).toInt //todo change this so crossover rate is randomly determined instead of forced

    val sorted = organisms.sortBy(_.fitness).reverse
    val breedingPool = sorted.slice(elitismNum, elitismNum + crossoverNum)

    //iterator to generate new children
    val c = Iterator.continually {
      //randomly select 2 chromosomes from the breeding pool to mate. Chromosomes can't mate with self
      val firstIndex = Random.nextInt(breedingPool.size)
      val secondIndex = Iterator.continually(Random.nextInt(breedingPool.size)).dropWhile(_ == firstIndex).next() //todo verify this

      Chromosome.uniformCrossover(
        breedingPool(firstIndex),
        breedingPool(secondIndex)
      )
    }.flatMap(e ⇒ List(e._1, e._2))

    val newGeneration = sorted.take(elitismNum) ++ c.take(sorted.size - elitismNum)
    val mutatedGeneration = newGeneration.map(c ⇒ if (Random.nextFloat() <= mutationRate) c.mutate() else c)

    organisms = mutatedGeneration
    numEvolutions += 1
  }
}
