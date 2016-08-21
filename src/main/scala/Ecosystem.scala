import scala.util.{Random, Try}

/**
  * Habitat for Chromosomes, used to simulate each evolution step
  *
  */
trait Ecosystem {

  var numEvolutions: Int = 0
  var organisms: List[Chromosome] = initialPopulation //the current inhabitants of this ecosystem

  val numChromosomes: Int
  val crossoverRate: Float
  val mutationRate: Float
  val elitismRate: Float

  /**
    * Randomly generate the appropriate number of chromosomes for an initial population
    *
    * @return the list of randomly generated chromosomes
    */
  def initialPopulation: List[Chromosome]

  /**
    * Perform an evolution step
    *
    */
  def evolve() = {
    println(s"printing generation $numEvolutions")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println(organisms.mkString("\n"))
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

    val elitismNum = (elitismRate * numChromosomes).toInt
    val crossoverNum = (crossoverRate * numChromosomes).toInt

    val sorted = organisms.sortBy(_.fitness).reverse
    val breedingPool = sorted.slice(elitismNum, elitismNum + crossoverNum)

    //iterator to generate new children
    val c = Iterator.continually {
      //randomly select 2 chromosomes from the breeding pool to mate. Chromosomes can't mate with self
      val firstIndex = Random.nextInt(breedingPool.size)
      val secondIndex = Iterator.continually(Random.nextInt(breedingPool.size)).dropWhile(_ == firstIndex).next()

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

  /**
    * Find the organism with the highest fitness in this Ecosystem
    *
    * @return Some(o) where o is the organism with the highest fitness,
    *         None if no organisms in this Ecosystem
    */
  def findAlphaOrganism(): Option[Chromosome] = Try(organisms.maxBy(_.fitness)).toOption

}
