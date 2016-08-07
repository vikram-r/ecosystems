
/**
  * Representation of a chromosome
  *
  */
object Chromosome {

  def apply(data: List[Int], fitnessFunc: (List[Int]) ⇒ Float)(implicit inputData: InputData): Chromosome = {
    new Chromosome(
      data = data,
      fitnessFunc = fitnessFunc
    )
  }

  //todo this needs to be re-thought
  /**
    * Mate 2 chromosomes, and return the resulting child
    *
    * @param first the first chromosome
    * @param second the second chromosome
    * @return the resulting child chromosome
    */
  def mate(first: Chromosome, second: Chromosome): Chromosome = ???

}

class Chromosome(data: List[Int], fitnessFunc: (List[Int]) ⇒ Float)(implicit val inputData: InputData) {

  lazy val fitness: Float = fitnessFunc(data)

  /**
    * Calculate this chromosome's fitness
    *
    * @return the fitness value of this chromosome. A value
    *         closer to 0 is a better fitness value
    */
  def getFitness(optimal: Float): Float = fitness

  /**
    * Return a new chromosome mutated from this one
    *
    * @return the mutated chromosome
    */
  def mutate(): Chromosome = ???

  /**
    * Determines whether this chromosome meets the provided fitness threshold to consider it a success
    *
    * @return true if this chromosome meets the threshold,
    *         false otherwise
    */
  def satisfiesThreshold() = {
    fitness >= inputData.optimalFitness * inputData.threshold
  }

  def getData = data

}
