import scala.util.Random

//todo eventually do fitness calculations in parallel
/**
  * Habitat for Organisms, used to simulate each evolution step
  *
  */
trait Ecosystem[T <: Organism[T]] {

  private var numEvolutions: Int = 0
  private var organisms = initialPopulation //the current inhabitants of this ecosystem

  val inputData: List[Int]
  val numOrganisms: Int
  val crossoverRate: Float
  val mutationRate: Float
  val elitismRate: Float
  val threshold: Float

  require(numOrganisms > 0, "numOrganisms must be > 0")

  case class ResultData(alphaOrganism: T,
                        metThreshold: Boolean,
                        numEvolutions: Int,
                        inputData: List[Int])

  /**
    * Randomly generate the appropriate number of organisms for an initial population
    *
    * @return the list of randomly generated organisms
    */
  def initialPopulation: List[T]

  /**
    * Start the simulation for the given number of cycles, or until the threshold is reached
    *
    * @param maxEvolutions the max number of times to evolve
    */
  def start(maxEvolutions: Int): ResultData = {
    require(maxEvolutions > 0, "maxEvolutions must be > 0")
    for(generation ← 0 until maxEvolutions) {
      println(s"generation: $generation")
      evolve()

      val mostFit = findAlphaOrganism()
      println(mostFit.data)
      println(s"highest fitness: ${mostFit.fitness}")

      if (mostFit.satisfiesThreshold(threshold)) {
        return ResultData(
          alphaOrganism = mostFit,
          metThreshold = true,
          numEvolutions = numEvolutions,
          inputData = inputData
        )
      }
    }
    ResultData(
      alphaOrganism = findAlphaOrganism(),
      metThreshold = false,
      numEvolutions = numEvolutions,
      inputData = inputData
    )
  }

  /**
    * Perform an evolution step
    *
    */
  def evolve() = {

    println(s"printing generation $numEvolutions")
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println(organisms.mkString("\n"))
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

    val elitismNum = (elitismRate * numOrganisms).toInt
    val crossoverNum = (crossoverRate * numOrganisms).toInt

    val sorted = organisms.sortBy(_.fitness).reverse
    val breedingPool = sorted.slice(elitismNum, elitismNum + crossoverNum)

    //iterator to generate new children
    val c = Iterator.continually {
      val (i, j) = selectParents(breedingPool)
      Organism.uniformCrossover(i, j, i.factory)
    }.flatMap(e ⇒ List(e._1, e._2))

    val newGeneration = sorted.take(elitismNum) ++ c.take(sorted.size - elitismNum)
    val mutatedGeneration = newGeneration.map(c ⇒ if (Random.nextFloat() <= mutationRate) c.mutate() else c)

    organisms = mutatedGeneration
    numEvolutions += 1
  }

  /**
    * Find the organism with the highest fitness in this Ecosystem
    *
    * @return the organism with the highest fitness
    */
  def findAlphaOrganism(): T = organisms.maxBy(_.fitness)

  /**
    * Determine how parents are selected from the breeding pool. This method selects 2 organisms at random, and ensures
    * that an organism cannot mate with itself.
    *
    * @param breedingPool the list of possible organisms that can become a parent
    * @return a tuple with the 2 selected organism parents
    */
  def selectParents(breedingPool: List[T]): (T, T) = {
    val i = Random.nextInt(breedingPool.size)
    val j = Iterator.continually(Random.nextInt(breedingPool.size)).dropWhile(_ == i).next()
    (breedingPool(i), breedingPool(j))
  }

}
