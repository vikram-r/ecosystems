package examples.knapsack

import core.Ecosystem

import scala.util.Random

class KnapsackEcosystem(val allItems: List[KnapsackItem],
                        val capacity: Int,
                        val numOrganisms: Int,
                        val crossoverRate: Float,
                        val mutationRate: Float,
                        val elitismRate: Float,
                        val tournamentSize: Int,
                        val threshold: Float) extends Ecosystem[KnapsackOrganism] {


  /**
    * Randomly generate the appropriate number of KnapsackOrganisms for an initial population
    *
    * @return the list of randomly generated organisms
    */
  override def initialPopulation: List[KnapsackOrganism] = {
    List.fill(numOrganisms)(new KnapsackOrganism(
      data = List.fill(allItems.size)(Random.nextInt(2)),
      allItems = allItems,
      capacity = capacity
    ))
  }

  /**
    * Override the default selection algorithm and use tournament selection instead
    */
  override def selectParents(breedingPool: List[KnapsackOrganism]): (KnapsackOrganism, KnapsackOrganism) = {
    def _tournamentSelection(): KnapsackOrganism = {
      Iterator.continually {
        breedingPool(Random.nextInt(breedingPool.size))
      }.take(tournamentSize).maxBy(_.fitness)
    }

    (_tournamentSelection(), _tournamentSelection())
  }

  /**
    * Basic logging
    *
    * @param result the result data
    */
  override def handleResult(result: ResultData): Unit = {
    val lastGenerationAlpha = result.alphaOrganisms.last
    println("**************************************")
    println(s"All possible KnapsackItems: \n${allItems.mkString("\n")}")

    println(s"Max capacity allowed: $capacity")
    println(s"Highest possible value: ${lastGenerationAlpha.optimalFitness}")

    println(s"Total value achieved: ${lastGenerationAlpha.fitness}")
    println(s"Capacity used: ${lastGenerationAlpha.capacityUsed}")

    println(s"Most fit data list: $lastGenerationAlpha")
  }
}
