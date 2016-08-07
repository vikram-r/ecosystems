import scala.util.Random

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

  /**
    * Mate 2 chromosomes, and return the resulting child
    *
    * @param first the first chromosome
    * @param second the second chromosome
    * @return the resulting child chromosome
    */
  def mate(first: Chromosome, second: Chromosome): Chromosome = {
    //todo this could probably be better
    //choose a random index, and use data of first until index, then second
    val index = Random.nextInt(first.getData.size)
    Chromosome(data = first.getData.take(index) ++ second.getData.drop(index), first.getFitnessFunc)(first.inputData)
  }

}

//todo consider using a Vector instead of a List to represent underlying data
//todo why is this not a case class?
class Chromosome(data: List[Int], fitnessFunc: (List[Int]) ⇒ Float)(implicit val inputData: InputData) {

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
    val i2 = Iterator.continually(Random.nextInt(data.size)).takeWhile(_ == i1).toList.last //make sure i2 != i1

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

  def getData = data

}
