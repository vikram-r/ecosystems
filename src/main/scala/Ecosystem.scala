import scala.util.Try

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
  def evolve()

  /**
    * Find the organism with the highest fitness in this Ecosystem
    *
    * @return Some(o) where o is the organism with the highest fitness,
    *         None if no organisms in this Ecosystem
    */
  def findAlphaOrganism(): Option[Chromosome] = Try(organisms.maxBy(_.fitness)).toOption

}
