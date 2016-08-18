import scala.util.Random

/**
  * Created by vikram on 8/4/16.
  */
sealed trait InputData {

  val data: List[Int]

  val size: Int = data.size

  val optimalFitness: Float

  val threshold: Float

  def newChromosomeDataElem(): Int //a way to define how a data element in a chromosome is randomly created

}

class MaxWeightedSumInputData(val data: List[Int],
                              val maxSum: Int,
                              val threshold: Float) extends InputData {

  override val optimalFitness: Float = data.max * 100

  override def newChromosomeDataElem() = Random.nextInt(maxSum)

}