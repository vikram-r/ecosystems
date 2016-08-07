/**
  * Created by vikram on 8/4/16.
  */
sealed trait InputData {

  val data: List[Int]

  val size: Int = data.size

  val optimalFitness: Float

  val threshold: Float

}

class MaxWeightedSumInputData(val data: List[Int],
                              val maxSum: Int,
                              val threshold: Float) extends InputData {

  require(data.sum == maxSum, s"MaxWeightedSumInputData must be a list of integers that sums $maxSum")
  println(s"Input Data: $data")

  //todo this fitness function is not correct
  override val optimalFitness: Float = data.max * maxSum

}