import scala.util.Random

/**
  * Created by vikram on 8/4/16.
  */
object ListHelpers {


  //Use this to override List builder methods like `List.fill`
  object RandomListFactory {

    /*
     * Generate an integer number line from 0 to sum, mark a point on both start and end, then on an additional
     * size - 1 random points between. Calculate the spaces between each random point on the line to generate a
     * random int list with size = size and all elements summing sum.
     */
    def randomListWithSum(size: Int, sum: Int): List[Int] = {
      (List.fill(size - 1)(Random.nextInt(sum - 1) + 1) ++ List(0, sum))
        .sorted
        .sliding(2)
        .map(tuple ⇒ tuple.last - tuple.head).toList
    }

  }

  implicit def randomListFactoryFromList(t: List.type): RandomListFactory.type = RandomListFactory

}
