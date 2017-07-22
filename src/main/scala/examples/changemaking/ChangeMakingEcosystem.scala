package examples.changemaking

import core.Ecosystem

import scala.util.Random


class ChangeMakingEcosystem(val allowedCoins: List[Int],
                            val targetValue: Int,
                            val numOrganisms: Int,
                            val crossoverRate: Float,
                            val mutationRate: Float,
                            val elitismRate: Float,
                            val threshold: Float) extends Ecosystem[ChangeMakingOrganism] {
  /**
    * Define the initial population of the ecosystem
    *
    * @return the list of randomly generated organisms
    */
  override def initialPopulation: List[ChangeMakingOrganism] = {
    List.fill(numOrganisms)(new ChangeMakingOrganism(
      data = newRandomChangeMakingOrganismData,
      allowedCoins = allowedCoins,
      targetValue = targetValue)
    )
  }

  /**
    * Handle the result after running the simulation
    *
    * @param result
    */
  override def handleResult(result: ResultData): Unit = {
    val lastGenerationAlpha = result.alphaOrganisms.last
    println("**************************************")
    println(s"allowedCoins: \n${allowedCoins.mkString("\n")}")
    println(s"targetValue: $targetValue")
    println(s"value achieved: ${lastGenerationAlpha.totalValue}")
    println(s"number coins used: ${lastGenerationAlpha.numberCoins}")

    println(s"coins used: ${allowedCoins.zip(lastGenerationAlpha.data).toMap}")
  }

  /**
    * Add random coins until the value is greater than or equal to the targetValue
    * @return the data list
    */
  private def newRandomChangeMakingOrganismData: List[Int] = {
    var sum = 0
    val freqMap = Iterator.continually {
      val i = Random.nextInt(allowedCoins.size)
      sum += allowedCoins(i)
      (i, sum)
    }.takeWhile(_._2 < targetValue) // keep generating new coins until right before targetValue is exceeded
      .map(_._1).toList.groupBy(i ⇒ i).mapValues(_.size) // create a frequency map for each coin

    // convert the frequency map into a list
    freqMap.foldLeft(allowedCoins.indices.map(_ → 0).toMap) {
      case (m, (coinType, freq)) ⇒
        m + (coinType → (m.getOrElse(coinType, 0) + freq))
    }.toList.sortBy(_._1).map(_._2)
  }
}
