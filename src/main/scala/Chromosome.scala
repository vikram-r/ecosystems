import scala.util.Random
import Chromosome._

/**
  * Representation of a chromosome
  *
  */

object Chromosome {

  /**
    * create a chromozone with random values for the given size
    *
    * @param size of the chromosome
    * @return
    */
  def apply(size: Int)(implicit IDEAL_FITNESS: IdealFitness): Chromosome = {
    val rawData = List.fill(size)(Random.nextDouble())
    val sum = rawData.sum
    new Chromosome(
      data = rawData.map(d ⇒ (d / sum) * 100) //normalize
    )
  }

  def mate(first: Chromosome, second: Chromosome): Chromosome = ???

  //todo this is a bit overkill for now, but it might be useful to keep the aggregated input list for a better fitness function
  /**
    * Representation object for the ideal fitness calculated from the input set
    *
    * @param idealSum
    */
  case class IdealFitness(idealSum: Double)
  object IdealFitness {
    def apply(input: List[List[Int]]): IdealFitness = {
      val combinedInputs = input.transpose.map(_.sum)
      IdealFitness(combinedInputs.size * 1) //todo eventually add negatives
    }
  }
}

//todo implicitly add IdealFitness
class Chromosome(data: List[Double])(implicit val IDEAL_FITNESS: IdealFitness) {

  lazy val fitness: Double = math.abs(data.sum - IDEAL_FITNESS.idealSum)

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
