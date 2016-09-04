import scala.util.Random

/**
  * Representation of a organism
  *
  */
object Organism {

  /**
    * Mate 2 organisms, using one point crossover
    *
    * @param first the first organism
    * @param second the second organism
    * @param factory a function that describes how to create a new instance of the Organism with the appropriate type
    *
    * @return the resulting children
    */
  def onePointCrossover[T <: Organism[T]](first: T, second: T, factory: (List[Int] ⇒ T)): (T, T) = {
    val index = Random.nextInt(first.data.size)
    val a = factory(first.data.take(index) ++ second.data.drop(index))
    val b = factory(second.data.take(index) ++ first.data.drop(index))
    (a, b)
  }

  /**
    * Mate 2 organisms using uniform crossover
    *
    * @param first the first organism
    * @param second the second organism
    * @param factory a function that describes how to create a new instance of the Organism with the appropriate type
    *
    * @return the resulting children
    */
  def uniformCrossover[T <: Organism[T]](first: T, second: T, factory: (List[Int] ⇒ T)): (T, T) = {
    val zipped = first.data.zip(second.data).map {
      e ⇒ if (Random.nextFloat() >= .50f) (e._1, e._2) else (e._2, e._1)
    }
    (factory(zipped.map(_._1)), factory(zipped.map(_._2)))
  }

}

//todo consider using a Vector instead of a List to represent underlying data
abstract class Organism[T <: Organism[T]](val data: List[Int]) {

  lazy val fitness: Float = fitnessFunction

  val optimalFitness: Float

  def factory: (List[Int]) ⇒ T

  /**
    * Calculate this organism's fitness. This method should not be called directly, use the lazy val instead
    *
    * @return the fitness value of this organism.
    *
    */
  def fitnessFunction: Float

  /**
    * Construct and return a new organism by mutating this one. This organism should not be changed.
    *
    * @return the modified organism
    */
  def mutate(): T

  /**
    * Determines whether this organism meets the provided fitness threshold to consider it a success
    *
    * @return true if this organism meets the threshold,
    *         false otherwise
    */
  def satisfiesThreshold(threshold: Float) = {
    fitness >= optimalFitness * threshold
  }

  override def toString: String = {
    data.toString()
  }

}