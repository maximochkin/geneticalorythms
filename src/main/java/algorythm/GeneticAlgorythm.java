package algorythm;

import crossover.methods.CrossoverMethod;
import crossover.strategies.CrossoverStrategy;
import entities.Person;
import entities.PersonTemplate;
import entities.Population;
import fitness.FitnessFunction;
import initializers.BasicRandomPopulationInitializer;
import initializers.PersonInitializer;
import initializers.PopulationInitializer;
import mutations.Mutation;

public class GeneticAlgorythm {
    private static final int MAX_SIZE_OF_POPULATION = 1000;
    private static final int INITIAL_SIZE_OF_POPULATION = 10;
    private static final double PRECISION = 0.01;
    private static final double MUTATION_PROBABILITY = 0.1;

    private PopulationInitializer populationInitializer;
    private PersonInitializer personInitializer;
    private Mutation mutation;
    private CrossoverMethod crossoverMethod;
    private CrossoverStrategy crossoverStrategy;
    private PersonTemplate personTemplate;
    private FitnessFunction fitnessFunction;

    public GeneticAlgorythm(PopulationInitializer populationInitializer, PersonInitializer personInitializer,
                            Mutation mutation, CrossoverMethod crossoverMethod, CrossoverStrategy crossoverStrategy,
                            PersonTemplate personTemplate, FitnessFunction fitnessFunction) {
        this.populationInitializer = populationInitializer;
        this.personInitializer = personInitializer;
        this.mutation = mutation;
        this.crossoverMethod = crossoverMethod;
        this.crossoverStrategy = crossoverStrategy;
        this.personTemplate = personTemplate;
        this.fitnessFunction = fitnessFunction;
    }

    public Person start(int numberOfIterations) {
        Population population = new BasicRandomPopulationInitializer()
                .initialize(INITIAL_SIZE_OF_POPULATION, personInitializer, personTemplate);

        population.calculateFitnessFunction(fitnessFunction);

        crossoverStrategy.crossover(population, crossoverMethod, fitnessFunction);

        mutation.mutate(population, personTemplate, MUTATION_PROBABILITY);

        population.sort();

        return population.getPersonByNumber(0);

    }

}
