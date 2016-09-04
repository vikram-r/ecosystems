import scala.util.Random

object MaxWeightedSumOrganism {
  def newBit(maxSum: Int) = Random.nextInt(maxSum)

  def newInstance(data: List[Int], maxSum: Int, inputData: List[Int]): MaxWeightedSumOrganism =
    new MaxWeightedSumOrganism(data, maxSum, inputData)
}

class MaxWeightedSumOrganism(data: List[Int], maxSum: Int, inputData: List[Int]) extends Organism[MaxWeightedSumOrganism](data) {

  override val optimalFitness: Float = inputData.max * maxSum

  override def factory: (List[Int]) ⇒ MaxWeightedSumOrganism =
    (d: List[Int]) ⇒ MaxWeightedSumOrganism.newInstance(d, maxSum, inputData)

  override def getFitness: Float = {
    val weightedSum = data.zip(inputData).map(e ⇒ e._1 * e._2).sum
    val percentOffConstraint = math.abs(data.sum - maxSum) / maxSum.toFloat

    weightedSum - (percentOffConstraint * weightedSum)
  }

  override def mutate(): MaxWeightedSumOrganism = {
    val i1 = Random.nextInt(data.size)
    val i2 = Iterator.continually(Random.nextInt(data.size)).dropWhile(_ == i1).next //make sure i2 != i1
    MaxWeightedSumOrganism.newInstance(
      data = data.updated(i1, MaxWeightedSumOrganism.newBit(maxSum)).updated(i2, MaxWeightedSumOrganism.newBit(maxSum)),
      maxSum = maxSum,
      inputData = inputData)
  }
}
