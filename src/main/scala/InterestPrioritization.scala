/**
  * Created by vikram on 8/1/16.
  */
object InterestPrioritization extends App {
  import Chromosome._

  val input: List[List[Float]] = List(List(.10f, .20f, .70f), List(.40f, .50f, .10f), List(.25f, .27f, .48f))

  require(input.map(_.size).distinct.size == 1, "all input rows should contain same number of columns")
  require(input.map(_.sum).toSet == Set(1f), "all input rows should total 1")


  //Hyperparameters //todo set these via command line

  //number of chromosomes per generation
  val NUM_CHROMOSOMES = 100

  //percent of chromosomes mutated per generation
  val MUTATION_RATE = .02f

  //percent of chromosomes preserved per generation
  val ELITISM_RATE = .01f

  //max number of evolution steps
  val MAX_EVOLUTIONS = 100

  //Acceptable threshold percentage from ideal for success
  val ACCEPTABLE_THRESHOLD = .001f

  implicit val IDEAL_FITNESS = IdealFitness(input, ACCEPTABLE_THRESHOLD)

  val ecosystem = new Ecosystem(
    numChromosomes = NUM_CHROMOSOMES,
    mutationRate = MUTATION_RATE,
    elitismRate = ELITISM_RATE
  )

  for {
    generation ← 0 until MAX_EVOLUTIONS
    mostFit = ecosystem.findAlphaOrganism()
    if !mostFit.exists(_.satisfiesThreshold())
  } {
    println(s"generation: $generation")
    println(s"highest fitness: ${mostFit.map(_.fitness + "").getOrElse("n/a")}")
    ecosystem.evolve()
  }

  val mostFit = ecosystem.findAlphaOrganism()
  println(mostFit.get.getData)


}
