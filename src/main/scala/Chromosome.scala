import scala.util.Random

/**
  * Representation of a chromosome
  *
  */
object Chromosome {

  /**
    * Mate 2 chromosomes, using one point crossover
    * @param first the first chromosome
    * @param second the second chromosome
    * @return the resulting children
    */
  def onePointCrossover(first: Chromosome, second: Chromosome): (Chromosome, Chromosome) = {
    val index = Random.nextInt(first.data.size)
    val a = Chromosome(data = first.data.take(index) ++ second.data.drop(index), first.getFitnessFunc)(first.inputData)
    val b = Chromosome(data = second.data.take(index) ++ first.data.drop(index), first.getFitnessFunc)(first.inputData)

    (a, b)
  }

  /**
    * Mate 2 chromosomes using uniform crossover
    * @param first the first chromosome
    * @param second the second chromosome
    * @return the resulting children
    */
  def uniformCrossover(first: Chromosome, second: Chromosome): (Chromosome, Chromosome) = {
    val zipped = first.data.zip(second.data).map {
      e ⇒ if (Random.nextFloat() >= .50f) (e._1, e._2) else (e._2, e._1)
    }
    val a = Chromosome(data = zipped.map(_._1), first.getFitnessFunc)(first.inputData)
    val b = Chromosome(data = zipped.map(_._2), first.getFitnessFunc)(first.inputData)

    (a, b)
  }

}

//todo consider using a Vector instead of a List to represent underlying data
//todo why is this not a case class?
case class Chromosome(data: List[Int], fitnessFunc: (List[Int]) ⇒ Float)(implicit val inputData: InputData) {

  lazy val fitness: Float = fitnessFunc(data)

  /**
    * Calculate this chromosome's fitness
    *
    * @return the fitness value of this chromosome. A value
    *         closer to 0 is a better fitness value
    */
  def getFitness(optimal: Float): Float = fitness

  def getFitnessFunc = fitnessFunc

  /**
    * Return a new chromosome mutated from this one
    *
    * @return the mutated chromosome
    */
  def mutate(): Chromosome = {
    //todo this should probably be better
    //at 2 random indexes, change the values with some other pair that totals the same sum

    val i1 = Random.nextInt(data.size)
    val i2 = Iterator.continually(Random.nextInt(data.size)).dropWhile(_ == i1).next //make sure i2 != i1

    val sum = i1 + i2
    val rand = Random.nextInt(sum + 1)

    Chromosome(data = data.updated(i1, rand).updated(i2, sum - rand), fitnessFunc = this.fitnessFunc)
  }

  /**
    * Determines whether this chromosome meets the provided fitness threshold to consider it a success
    *
    * @return true if this chromosome meets the threshold,
    *         false otherwise
    */
  def satisfiesThreshold() = {
    fitness >= inputData.optimalFitness * inputData.threshold
  }

}
