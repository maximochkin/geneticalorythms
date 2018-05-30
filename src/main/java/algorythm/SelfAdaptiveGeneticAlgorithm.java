package algorythm;

import crossover.methods.CrossoverMethod;
import crossover.strategies.CrossoverStrategy;
import entities.Person;
import entities.PersonTemplate;
import entities.Population;
import fitness.FitnessFunction;
import initializers.PersonInitializer;
import initializers.PopulationInitializer;
import mutations.Mutation;
import selection.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelfAdaptiveGeneticAlgorithm {
	private static final int NUMBER_OF_ITERATIONS = 500;
	private static final int NUMBER_OF_POPULATIONS = 5;
	private static final int ITERATIONS_WITHOUT_BEST_CHANGES = 50;
	private static final int MAX_SIZE_OF_POPULATION = 50000 / NUMBER_OF_POPULATIONS;
	private static final int INITIAL_SIZE_OF_POPULATION = 10000 / NUMBER_OF_POPULATIONS;
	private static final int NUMBER_OF_MIGRATED_PERSONS = 10;
	private static final double PRECISION = 0.1;
	private static final double MUTATION_PROBABILITY = 0.1;
	private static final double CROSSOVER_PROBABILITY = 0.8;
	private static final Logger LOGGER = Logger.getLogger(SelfAdaptiveGeneticAlgorithm.class.getName());

	private static class PopulationParameters {
		private int populationSize;
		private double mutationProbability;
		private double crossoverProbability;
		private Person bestPerson;
		private double averageFitness;
		private int iterationsWithTheSameBestPersonCounter = 0;

		PopulationParameters(int populationSize, double mutationProbability, double crossoverProbability) {
			this.populationSize = populationSize;
			this.mutationProbability = mutationProbability;
			this.crossoverProbability = crossoverProbability;
		}
	}

	private PopulationInitializer populationInitializer;
	private PersonInitializer personInitializer;
	private Mutation mutation;
	private CrossoverMethod crossoverMethod;
	private CrossoverStrategy crossoverStrategy;
	private PersonTemplate personTemplate;
	private FitnessFunction fitnessFunction;
	private Selector selector;

	public SelfAdaptiveGeneticAlgorithm(PopulationInitializer populationInitializer, PersonInitializer personInitializer,
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

		List<Population> populations = new ArrayList<>();
		List<PopulationParameters> populationParameters = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_POPULATIONS; i++) {
			Population population = populationInitializer.initialize(INITIAL_SIZE_OF_POPULATION, personInitializer, personTemplate);
			population.calculateFitnessFunction(fitnessFunction);
			population.sort();
			populations.add(population);
			PopulationParameters parameters = new PopulationParameters(INITIAL_SIZE_OF_POPULATION, MUTATION_PROBABILITY, CROSSOVER_PROBABILITY);
			populationParameters.add(parameters);
		}

		Person bestOfTheBest = null;
		Person localBest;
		int iterationWhenWeFoundBest = 0;
		boolean firstTime = true;
		int iterationWhenWeFoundAppropriateSolution = 0;

		for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {

			for (int j = 0; j < NUMBER_OF_POPULATIONS; j++) {

				Population population = populations.get(j);
				PopulationParameters parameters = populationParameters.get(j);

				List<Person> children = crossoverStrategy.crossover(population, crossoverMethod, parameters.crossoverProbability);

				mutation.mutate(population, personTemplate, parameters.mutationProbability);

				population.calculateFitnessFunction(fitnessFunction);

				children.forEach(fitnessFunction::apply);

				selector.select(population, parameters.populationSize, children);

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

				parameters.averageFitness = averageFitnessOfPopulation;

				if (parameters.bestPerson != null && parameters.bestPerson.equals(localBest)) {
					parameters.iterationsWithTheSameBestPersonCounter++;
				} else {
					parameters.bestPerson = localBest;
					parameters.iterationsWithTheSameBestPersonCounter = 0;
				}

				LOGGER.info("STEP: " + i + " | POPULATION #"+ j + " | " + population.getPersonByNumber(0).toString());
				LOGGER.info("STEP: " + i + " | POPULATION #"+ j + " | AverageFitness = " + averageFitnessOfPopulation);
				LOGGER.info("STEP: " + i + " | POPULATION #"+ j + " | SIZE: " + parameters.populationSize);
				LOGGER.info("STEP: " + i + " | POPULATION #"+ j + " | CROSSOVER: " + parameters.crossoverProbability);
				LOGGER.info("STEP: " + i + " | POPULATION #"+ j + " | MUTATIONS: " + parameters.mutationProbability);
			}

			modifyParameters(populationParameters, populations);

			System.out.println("Iteration when we found appropriate: " + iterationWhenWeFoundAppropriateSolution);
			System.out.println("Iteration when we found best: " + iterationWhenWeFoundBest);
		}

		return bestOfTheBest;

	}

	private void modifyParameters(List<PopulationParameters> populationParameters, List<Population> populations) {
		double averageFitnessForAllEcosystem = populations.stream()
				.map(Population::getPopulation)
				.flatMap(Collection::stream)
				.mapToDouble(Person::getFitnessValue)
				.average()
				.getAsDouble();

		double averageSize = populations.stream().mapToDouble(Population::getSize).average().getAsDouble();

		for (int i = 0; i < populations.size(); i++) {
			PopulationParameters params = populationParameters.get(i);
			params.mutationProbability = params.mutationProbability + (averageSize / params.populationSize - 1) * 0.0001;
			params.crossoverProbability = params.crossoverProbability + (averageSize / params.populationSize - 1) * 0.001;
			if (params.mutationProbability < 0) {
				params.mutationProbability = 0;
			}
			if (params.crossoverProbability < 0) {
				params.crossoverProbability = 0;
			}
			params.populationSize = (int) (params.populationSize + params.bestPerson.getFitnessValue()/averageFitnessForAllEcosystem - 1);
			if (params.iterationsWithTheSameBestPersonCounter == ITERATIONS_WITHOUT_BEST_CHANGES) {
				migrate(populations.get(i), populations.get(ThreadLocalRandom.current().nextInt(NUMBER_OF_POPULATIONS)));
				params.iterationsWithTheSameBestPersonCounter = 0;
			}
		}
	}

	private void migrate(Population to, Population from) {
		List<Person> migratedPersons = IntStream.range(0, NUMBER_OF_MIGRATED_PERSONS)
				.mapToObj(from::getPersonByNumber)
				.collect(Collectors.toList());

		to.add(migratedPersons);

		to.distinct();
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
