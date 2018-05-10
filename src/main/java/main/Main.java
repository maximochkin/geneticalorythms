package main;

import algorythm.GeneticAlgorythm;
import crossover.methods.BasicOnePointCrossover;
import crossover.methods.CrossoverMethod;
import crossover.strategies.CrossoverStrategy;
import crossover.strategies.RouletteCrossoverStrategy;
import entities.PersonTemplate;
import fitness.FitnessFunction;
import fitness.ForTestingFitnessFunction;
import initializers.BasicRandomPersonInitializer;
import initializers.BasicRandomPopulationInitializer;
import initializers.PersonInitializer;
import initializers.PopulationInitializer;
import mutations.BasicRandomBasedMutation;
import mutations.Mutation;
import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static final int TEMPLATE_SIZE = 5;
	public static final int NUMBER_OF_ITERATIONS = 100;

	public static void main(String[] args) {
		PopulationInitializer populationInitializer = new BasicRandomPopulationInitializer();
		PersonInitializer personInitializer = new BasicRandomPersonInitializer();
		Mutation mutation = new BasicRandomBasedMutation();
		CrossoverMethod crossoverMethod = new BasicOnePointCrossover();
		CrossoverStrategy crossoverStrategy = new RouletteCrossoverStrategy();
		PersonTemplate personTemplate = preparePersonTemplate();
		FitnessFunction fitnessFunction = new ForTestingFitnessFunction();

		new GeneticAlgorythm(populationInitializer, personInitializer, mutation, crossoverMethod, crossoverStrategy,
				personTemplate, fitnessFunction).start(NUMBER_OF_ITERATIONS);
	}

	private static PersonTemplate preparePersonTemplate() {
		List<Pair<Integer, Integer>> limits = Arrays.asList(
				new Pair<>(0, 10),
				new Pair<>(5, 7),
				new Pair<>(10, 20),
				new Pair<>(1, 10),
				new Pair<>(2, 8)
		);

		PersonTemplate template = new PersonTemplate(TEMPLATE_SIZE, limits);
		return template;
	}
}
