package examples.knapsack

import core.Organism

import scala.util.Random

/**
  * Representation of a single item in the Knapsack
  *
  * @param weight the weight of the item
  * @param value the value of the item
  */
case class KnapsackItem(weight: Int, value: Int)

object KnapsackItem {

  def randomItem(maxWeight: Int, maxValue: Int) = new KnapsackItem(Random.nextInt(maxWeight + 1), Random.nextInt(maxValue + 1))

}

/**
  * Custom Organism used for solving the Knapsack Problem. Each element of data can either be 1 or 0. 1 means that the
  * corresponding KnapsackItem is in the knapsack, 0 means it is not.
  *
  * @param data the bit data of the organism
  * @param allItems list of all possible KnapsackItems
  * @param capacity the max capacity of the knapsack
  */
class KnapsackOrganism(data: List[Int], allItems: List[KnapsackItem], capacity: Int) extends Organism[KnapsackOrganism](data){

  require(data.size == allItems.size, "there must be a data bit for each possible KnapsackItem")

  override val optimalFitness: Float = allItems.map(_.value).sum

  override def factory: (List[Int]) ⇒ KnapsackOrganism =
    (d: List[Int]) ⇒ new KnapsackOrganism(d, allItems, capacity)

  /**
    * Flip the bit of a random item in the knapsack
    *
    * @return the modified organism
    */
  override def mutate(): KnapsackOrganism = {
    val i = Random.nextInt(data.size)
    new KnapsackOrganism(
      data = data.updated(i, 1 - data(i)),
      capacity = capacity,
      allItems = allItems)
  }

  /**
    * If the items in the knapsack exceed the capacity, the fitness is 0. Otherwise, the fitness is the sum of all the
    * values of items in the knapsack.
    *
    * @return the fitness value of this organism.
    *
    */
  override def fitnessFunction: Float = {
    val inKnapsack = data.zip(allItems).filterNot(_._1 == 0).map(_._2)
    if (inKnapsack.map(_.weight).sum > capacity) return 0
    inKnapsack.map(_.value).sum
  }

  def capacityUsed: Int =
    data.zip(allItems).filterNot(_._1 == 0).map(_._2.weight).sum

}
