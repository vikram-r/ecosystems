import scala.util.Random

/**
  * Created by vikram on 8/4/16.
  */
sealed trait InputData {

  val inputData: List[Int]

  val size: Int = inputData.size

  val optimalFitness: Float

  val threshold: Float

  def newChromosomeDataElem(): Int //a way to define how a data element in a chromosome is randomly created

  def fitnessFunc(c: Chromosome): Float //a function that determines the fitness of the given chromosome

}

class MaxWeightedSumInputData(val inputData: List[Int],
                              val maxSum: Int,
                              val threshold: Float) extends InputData {

  override val optimalFitness: Float = inputData.max * 100

  override def newChromosomeDataElem() = Random.nextInt(maxSum)

  /**
    * This fitness function calculates the dot product, and penalizes based
    * on how far the data is from meeting the constraint (all data values sum maxSum)
    */
  override def fitnessFunc(c: Chromosome): Float = {
    val weightedSum = c.data.zip(inputData).map(e ⇒ e._1 * e._2).sum
    val percentOffConstraint = math.abs(c.data.sum - maxSum) / maxSum.toFloat

    weightedSum - (percentOffConstraint * weightedSum)
  }

}