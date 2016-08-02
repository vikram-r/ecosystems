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
    val rawData = List.fill(size)(Random.nextInt(100))
    val sum = rawData.sum
    new Chromosome(
      data = rawData.map(d ⇒ (d / sum) * 100) //normalize //todo this arithmetic is wrong
    )
  }

  /**
    * Mate 2 chromosomes, and return the resulting child
    *
    * @param first the first chromosome
    * @param second the second chromosome
    * @return the resulting child chromosome
    */
  def mate(first: Chromosome, second: Chromosome): Chromosome = ???

  /**
    * Representation object for the ideal fitness calculated from the input set
    *
    * @param idealSum the floored sum of all elements in aggregatedInput]
    * @param chromosomeSize the size that every chromosome will be for this input //todo maybe not needed, makes a bit more readable
    * @param aggregatedInput the flattened input. The input is flattened by summing the
    *                        values of each column.
    * @return the resulting child chromosome
    */
  case class IdealFitness(idealSum: Int, thresholdFitness: Int, chromosomeSize: Int, aggregatedInput: List[Float])
  object IdealFitness {
    def apply(input: List[List[Float]], threshold: Float): IdealFitness = {
      require(threshold >= 0f && threshold <= 1f, "Acceptable threshold percentage must be between 0 and 1")
      val combinedInputs = input.transpose.map(_.sum)
      val idealSum = combinedInputs.size * 1
      IdealFitness(
        idealSum = idealSum,
        thresholdFitness = (idealSum - (idealSum * threshold)).toInt, //todo double check this int cast
        combinedInputs.size,
        combinedInputs
      ) //todo eventually add negatives
    }
  }
}

//todo implicitly add IdealFitness
class Chromosome(data: List[Int])(implicit val IDEAL_FITNESS: IdealFitness) {

  //todo while this is cool, I may need to optimize
  lazy val fitness: Float = {
    //apply this chromosome to the input, and sum the values to see how close it is to the ideal sum
    val numericSum = data.zip(IDEAL_FITNESS.aggregatedInput).map(e ⇒ e._1 * e._2).sum
    math.abs(numericSum - IDEAL_FITNESS.idealSum) //todo maybe not abs, might be good to know whether to increase or decrease
  }

  /**
    * Calculate this chromosome's fitness
    *
    * @return the fitness value of this chromosome. A value
    *         closer to 0 is a better fitness value
    */
  def getFitness(optimal: Float): Float = fitness

  /**
    * Return a new chromosome mutated from this one
    *
    * @return the mutated chromosome
    */
  def mutate(): Chromosome = ???

  /**
    * Determines whether this chromosome meets the provided fitness threshold to consider it a success
    *
    * @return true if this chromosome meets the threshold,
    *         false otherwise
    */
  def satisfiesThreshold() = IDEAL_FITNESS.thresholdFitness < fitness

  def getData = data

}
