package examples.changemaking

import core.Organism

import scala.util.Random

/**
  * Custom Organism used for solving the Change Making Problem. Each element of data represents the number of a particular
  * type of coin. The value at each index in data aligns with the coin value at the same index in allowedCoins.
  *
  * @param data The number of each coin this Organism represents. This will be zipped with allowedCoins
  * @param allowedCoins list of all possible coins
  * @param targetValue the desired value to create
  */
class ChangeMakingOrganism(data: List[Int], allowedCoins: List[Int], targetValue: Int) extends Organism[ChangeMakingOrganism](data) {
  require(allowedCoins.size == allowedCoins.distinct.size, "allowedCoints must contain unique values")
  require(data.size == allowedCoins.size, "data size must equal number of allowed coins (each element represents number of particular coin type)")

  override val optimalFitness: Float = Integer.MAX_VALUE

  // the change value this organism represents
  lazy val totalValue = data.zip(allowedCoins).map {
    case (numCoins, coinValue) ⇒ numCoins * coinValue
  }.sum

  lazy val numberCoins = data.sum

  override def factory: (List[Int]) ⇒ ChangeMakingOrganism =
    (d: List[Int]) ⇒ new ChangeMakingOrganism(d, allowedCoins, targetValue)

  /**
    * Randomly add or remove a coin
    *
    * @return the modified organism
    */
  override def mutate(): ChangeMakingOrganism = {
    val i = Random.nextInt(data.size)
    new ChangeMakingOrganism(
      data = data.updated(i, {
        val currentNum = data(i)
        if (Random.nextFloat >= .5f)
          if (currentNum > 0) currentNum - 1 else currentNum
        else currentNum + 1
      }),
      allowedCoins = allowedCoins,
      targetValue = targetValue
    )
  }

  /**
    * Calculate this organism's fitness. This method should not be called directly, use the lazy val instead
    *
    * @return the fitness value of this organism.
    *
    */
  override def fitnessFunction: Float = {
    val offfset = math.abs(targetValue - totalValue) * -1
    offfset * (numberCoins + 1)
  }
}