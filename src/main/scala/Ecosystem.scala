import java.util.concurrent.Executors

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.Random

//todo eventually do fitness calculations in parallel
/**
  * Habitat for Organisms, used to simulate each evolution step
  *
  */
trait Ecosystem[T <: Organism[T]] {

  private var numEvolutions: Int = 0
  private var organisms = initialPopulation //the current inhabitants of this ecosystem

  val numOrganisms: Int
  val crossoverRate: Float
  val mutationRate: Float
  val elitismRate: Float
  val threshold: Float

  require(numOrganisms > 0, "numOrganisms must be > 0")

  case class ResultData(alphaOrganism: T,
                        metThreshold: Boolean,
                        numEvolutions: Int)

  val executor = {
    val numThreads = Runtime.getRuntime.availableProcessors()
    Executors.newFixedThreadPool(numThreads)
  }
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(executor)

  /**
    * Additional methods for dealing with an entire population of organisms. Populations are
    * represented as a List of Organisms
    *
    * @param population the organisms in this Population
    */
  implicit class Population(population: List[T]) {

    /**
      * Apply mutations at a given rate to the population
      *
      * @param rate the probability that a mutation is applied per organism
      * @return the mutated population
      */
    def applyMutation(rate: Float): List[T] =
      population.map(c ⇒ if (Random.nextFloat() <= rate) c.mutate() else c)

    //todo Should this use a fitness proportionate selction algorithm? Should it be user definable?
    /**
      * Select the given number of organisms designated for breeding from this population.
      *
      * @param n the number of organisms that should be in the breeding pool
      * @return "n" organisms that are candidates for breeding, or less if the population size is smaller than "n"
      */
    def takeBreedingPool(n: Int): List[T] = population.take(n)

    /**
      * Breed this population until the given number of children are generated. Calls [[selectParents()]] to
      * determine the parents.
      *
      * @param n the number of children to breed
      * @return n child organisms bred from the Population
      */
    def breedChildren(n: Int): List[T] = {
      Iterator.continually {
        val (i, j) = selectParents(population)
        Organism.uniformCrossover(i, j, i.factory)
      }.flatMap(c ⇒ List(c._1, c._2)).take(n).toList
    }
  }

  /**
    * Define the initial population of the ecosystem
    *
    * @return the list of randomly generated organisms
    */
  def initialPopulation: List[T]

  /**
    * Start the simulation for the given number of cycles, or until the threshold is reached
    *
    * @param maxEvolutions the max number of times to evolve
    */
  def run(maxEvolutions: Int) = {
    require(maxEvolutions > 0, "maxEvolutions must be > 0")
    handleResult(_run(maxEvolutions))
    executor.shutdown()
  }

  private def _run(maxEvolutions: Int): ResultData = {
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
          numEvolutions = numEvolutions
        )
      }
    }
    ResultData(
      alphaOrganism = findAlphaOrganism(),
      metThreshold = false,
      numEvolutions = numEvolutions
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

    // compute the fitness of all organisms in parallel
    val futures = for (o ← organisms) yield Future {
      o.fitness
    }

    // wait for all fitness values to be computed
    Await.result(Future.sequence(futures), Duration.Inf)

    val sorted = organisms.sortBy(_.fitness).reverse
    val (elitists, plebians) = sorted.splitAt(elitismNum)
    organisms = (
      elitists ++ plebians.takeBreedingPool(crossoverNum).breedChildren(sorted.size - elitists.size)
      ).applyMutation(mutationRate)
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


  /**
    * Handle the result after running the simulation
    *
    * @param result
    */
  def handleResult(result: ResultData)

}
