# Ecosystems

Ecosystems are an easily extendable way to quickly define and test genetic algorithms. By defining an ecosystem, a user will be able to specify an input data set, fitness function, a variety of hyperparameters, and more. The goal is to make the underlying genetic algorithm plumbing as generic as possible.

This project has 2 examples included:

1. **MaxWeightedSum**: Solves the trivial problem of finding an array of the same size as an input array such that it both sums a given value, and maximizes the result of the dot product between itself and the input array. This problem is trivial because the optimal solution is always of the form `[0,0,0,SUM,0]`, where the index of SUM is the index of the maximum value in the input array.

2. **Knapsack Problem**: [The Knapsack Problem](https://en.wikipedia.org/wiki/Knapsack_problem)


## Usage

Ecosystems is built with sbt. Simply use
```
sbt run
```

## Extending Ecosystems

In order to define your own problem, you need to provide your own implementation of both an `Organism`, and an `Ecosystem`. 

### Organisms

Extending `Organism` allows you to define the basic rules for each candidate solution, or "chromosome". All `Organisms` contain a `List[Int]` data field. Your class definition might look something like this:

```scala
class CustomOrganism(data: List[Int]
                       /* more custom parameters */
                      ) extends Organism[CustomOrganism](data){
```

Now you should override the following:

1. `mutate`: Defines how your custom `Organism` mutates itself. This method should not modify the internal state of `this` object, but instead return a new instance of your custom mutated `Organism`.

2. `fitnessFunction`: Defines how your custom `Organism` determines it's own fitness value.

3. `optimalFitness`: Defines the best possible fitness for this custom `Organism` type. In the `MaxWeightedSum` example, the `optimalFitness` is easy to define, since the best case solution is known. This will almost never be the case in reality, so an "estimate" is sufficient here. If even that is not possible, you can simply use `Integer.MAX_VALUE`, or `Integer.MIN_VALUE`, depending on how your fitness function is defined. 

4. `factory`: this method should define a first class function that describes how to create a new instance of your custom `Organism` (essentially a constructor). The reason for this is to ensure that generic operations, such as [crossover functions](https://en.wikipedia.org/wiki/Crossover_(genetic_algorithm)), will produce a new instance of your custom `Organism`. This is also why the `Organism` class that you extend uses an [F-bounded type parameter](https://tpolecat.github.io/2015/04/29/f-bounds.html). 


### Ecosystems

Extending an `Ecosystem` allows you to define various properties about the simulation itself. First you should extend `Ecosystem` and pass your custom `Organism` as it's type parameter:

```scala
class CustomEcosystem(val numOrganisms: Int,
                      val crossoverRate: Float,
                      val mutationRate: Float,
                      val elitismRate: Float,
                      val threshold: Float,
                      /* more custom parameters */) extends Ecosystem[CustomOrganism] {
```

The required methods to override here are: 

1. `initialPopulation`: This method allows you to define the starting population of `Organisms`. You should ensure that you generate at least `numOrganisms` elements.

2. `handleResult`: This method lets you decide what to do with the results of the simulation.

You can also optionally override `selectParents`, which allows you to change how parent `Organisms` are selected for breeding. By default, 2 random and unique `Organisms` are selected.

### Running your Simulation

To run your simulation, simply create a new instance of your custom `Ecosystem`, then call the `run` method.
