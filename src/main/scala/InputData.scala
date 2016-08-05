/**
  * Created by vikram on 8/4/16.
  */
sealed trait InputData {

  val data: List[Int]

  val size: Int = data.size

  val optimalFitness: Float

  val threshold: Float

}

class MaxWeightedSumInputData(val data: List[Int], val threshold: Float) extends InputData {

  override val optimalFitness: Float = data.max * size

}