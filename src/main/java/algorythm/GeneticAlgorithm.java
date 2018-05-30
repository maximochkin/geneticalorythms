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
import selection.Selector;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GeneticAlgorithm {
    private static final int NUMBER_OF_ITERATIONS = 500;
    private static final int MAX_SIZE_OF_POPULATION = 10000;
    private static final int INITIAL_SIZE_OF_POPULATION = 10000;
    private static final double PRECISION = 0.1;
    private static final double MUTATION_PROBABILITY = 0.1;
    private static final double CROSSOVER_PROBABILITY = 1.0;
    private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class.getName());


    private PopulationInitializer populationInitializer;
    private PersonInitializer personInitializer;
    private Mutation mutation;
    private CrossoverMethod crossoverMethod;
    private CrossoverStrategy crossoverStrategy;
    private PersonTemplate personTemplate;
    private FitnessFunction fitnessFunction;
    private Selector selector;

    public GeneticAlgorithm(PopulationInitializer populationInitializer, PersonInitializer personInitializer,
                            Mutation mutation, CrossoverMethod crossoverMethod, CrossoverStrategy crossoverStrategy,
                            PersonTemplate personTemplate, FitnessFunction fitnessFunction, Selector selector) {
        this.populationInitializer = populationInitializer;
        this.personInitializer = personInitializer;
        this.mutation = mutation;
        this.crossoverMethod = crossoverMethod;
        this.crossoverStrategy = crossoverStrategy;
        this.personTemplate = personTemplate;
        this.fitnessFunction = fitnessFunction;
        this.selector = selector;
    }

    public Person start() {

        initLogging();

        Population population = new BasicRandomPopulationInitializer()
                .initialize(INITIAL_SIZE_OF_POPULATION, personInitializer, personTemplate);

        population.calculateFitnessFunction(fitnessFunction);
        population.sort();

        Person bestOfTheBest = null;
        Person localBest;
        int iterationWhenWeFoundBest = 0;
        boolean firstTime = true;
        int iterationWhenWeFoundAppropriateSolution = 0;

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {

            List<Person> children = crossoverStrategy.crossover(population, crossoverMethod, CROSSOVER_PROBABILITY);

            mutation.mutate(population, personTemplate, MUTATION_PROBABILITY);

            population.calculateFitnessFunction(fitnessFunction);

            children.forEach(fitnessFunction::apply);

            selector.select(population, MAX_SIZE_OF_POPULATION, children);

            population.sort();

            localBest = population.getPersonByNumber(0);
            if (bestOfTheBest == null || localBest.getFitnessValue() > bestOfTheBest.getFitnessValue()) {
                bestOfTheBest = localBest.copy();
                iterationWhenWeFoundBest = i;
            }

            if (localBest.getFitnessValue() > PRECISION && firstTime) {
				iterationWhenWeFoundAppropriateSolution = i;
				firstTime = false;
			}

			double averageFitnessOfPopulation = population.getPopulation().stream()
                    .mapToDouble(Person::getFitnessValue)
                    .average()
                    .getAsDouble();

            LOGGER.info("STEP: " + i + " | " + population.getPersonByNumber(0).toString());
            LOGGER.info("STEP: " + i + " | AverageFitness = " + averageFitnessOfPopulation);
        }

		LOGGER.info("Iteration when we found appropriate: " + iterationWhenWeFoundAppropriateSolution);
		LOGGER.info("Iteration when we found best: " + iterationWhenWeFoundBest);
		LOGGER.info("Best for model: " + bestOfTheBest.toString());

        return bestOfTheBest;

    }

    private void initLogging() {
        try {
            Handler fh = new FileHandler("app.log", false);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.setLevel(Level.ALL);
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
