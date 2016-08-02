import scala.util.Random

/**
  * Representation of a chromosome
  *
  */

object Chromosome {

  /**
    * create a chromozone with random values for the given size
    * @param size
    * @return
    */
  def apply(size: Int): Chromosome = {
    val rawData = List.fill(size)(Random.nextDouble())
    val sum = rawData.sum
    new Chromosome(
      data = rawData.map(_ / sum) //normalize
    )
  }

  def mate(first: Chromosome, second: Chromosome): Chromosome = ???
}

class Chromosome(data: List[Double]) {

  val IDEAL_SUM = data.size * 1 //todo eventually add negatives

  lazy val fitness: Double = math.abs(data.sum - IDEAL_SUM)

  /**
    * Calculate this chromosome's fitness
    *
    * @return the fitness value of this chromosome. A value
    *         closer to 0 is a better fitness value
    */
  def getFitness(optimal: Double): Double = fitness

  /**
    * Return a new chromosome mutated from this one
    *
    * @return the mutated chromosome
    */
  def mutate(): Chromosome = ???

}
