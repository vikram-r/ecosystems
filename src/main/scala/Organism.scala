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
    * @return the resulting children
    */
  def onePointCrossover(first: Organism, second: Organism): (Organism, Organism) = {
    val index = Random.nextInt(first.data.size)
    val a = Organism(data = first.data.take(index) ++ second.data.drop(index), first.getFitnessFunc)(first.inputData)
    val b = Organism(data = second.data.take(index) ++ first.data.drop(index), first.getFitnessFunc)(first.inputData)

    (a, b)
  }

  /**
    * Mate 2 organisms using uniform crossover
    *
    * @param first the first organism
    * @param second the second organism
    * @return the resulting children
    */
  def uniformCrossover(first: Organism, second: Organism): (Organism, Organism) = {
    val zipped = first.data.zip(second.data).map {
      e ⇒ if (Random.nextFloat() >= .50f) (e._1, e._2) else (e._2, e._1)
    }
    val a = Organism(data = zipped.map(_._1), first.getFitnessFunc)(first.inputData)
    val b = Organism(data = zipped.map(_._2), first.getFitnessFunc)(first.inputData)

    (a, b)
  }

}

//todo consider using a Vector instead of a List to represent underlying data
case class Organism(data: List[Int], fitnessFunc: (Organism) ⇒ Float)(implicit val inputData: InputData) {

  lazy val fitness: Float = fitnessFunc(this)

  /**
    * Calculate this organism's fitness
    *
    * @return the fitness value of this organism. A value
    *         closer to 0 is a better fitness value
    */
  def getFitness(optimal: Float): Float = fitness

  def getFitnessFunc = fitnessFunc

  /**
    * Return a new organism mutated from this one
    *
    * @return the mutated organism
    */
  def mutate(): Organism = {
    val i1 = Random.nextInt(data.size)
    val i2 = Iterator.continually(Random.nextInt(data.size)).dropWhile(_ == i1).next //make sure i2 != i1
    Organism(data = data.updated(i1, inputData.newOrganismDataElem()).updated(i2, inputData.newOrganismDataElem()), fitnessFunc = this.fitnessFunc)
  }

  /**
    * Determines whether this organism meets the provided fitness threshold to consider it a success
    *
    * @return true if this organism meets the threshold,
    *         false otherwise
    */
  def satisfiesThreshold() = {
    fitness >= inputData.optimalFitness * inputData.threshold
  }

}
