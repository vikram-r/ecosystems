import scala.util.Try
import Chromosome._

//todo eventually do fitness calculations in parallel
/**
  * Habitat for Chromosomes, used to simulate each evolution step
  */
class Ecosystem(val numChromosomes: Int,
                val mutationRate: Float,
                val elitismRate: Float)(implicit val idealFitness: IdealFitness) {

  var numEvolutions: Int = 0

  var organisms: List[Chromosome] = initialPopulation //the current inhabitants of this ecosystem

  /**
    * Randomly generate the appropriate number of chromosomes for an initial population
    *
    * @return the list of randomly generated chromosomes
    */
  def initialPopulation: List[Chromosome] =
    List.fill(numChromosomes)(Chromosome.apply(idealFitness.chromosomeSize))

  /**
    * Perform an evolution step
    *
    */
  def evolve(): Unit = {
    println("evolving!")
    //todo FUN STUFF

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
