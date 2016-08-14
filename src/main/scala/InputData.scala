import scala.util.Random

/**
  * Created by vikram on 8/4/16.
  */
sealed trait InputData {

  val data: List[Int]

  val size: Int = data.size

  val optimalFitness: Float

  val threshold: Float

  val newChromosomeDataElem: () ⇒ Int //a way to define how a data element in a chromosome is randomly created

}

class MaxWeightedSumInputData(val data: List[Int],
                              val maxSum: Int,
                              val threshold: Float) extends InputData {

  //todo for now I simplified this fitness function, because crossover was unable to meet the all output data sums maxSum constraint

  override val optimalFitness: Float = data.sum * 100

  override val newChromosomeDataElem = () ⇒ Random.nextInt(maxSum)

}