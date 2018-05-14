package main;

import algorythm.GeneticAlgorythm;
import crossover.methods.BasicOnePointCrossover;
import crossover.methods.CrossoverMethod;
import crossover.strategies.CrossoverStrategy;
import crossover.strategies.RouletteCrossoverStrategy;
import crossover.strategies.TournamentCrossoverStrategy;
import entities.PersonTemplate;
import fitness.AvgSqrFitnessFunctionForFacility;
import fitness.FitnessFunction;
import fitness.ForTestingFitnessFunction;
import initializers.BasicRandomPersonInitializer;
import initializers.BasicRandomPopulationInitializer;
import initializers.PersonInitializer;
import initializers.PopulationInitializer;
import mutations.BasicRandomBasedMutation;
import mutations.Mutation;
import selection.RemoveTheWorstSelector;
import selection.Selector;
import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static final int TEMPLATE_SIZE = 6;
	public static final int NUMBER_OF_ITERATIONS = 200;

	public static void main(String[] args) {
		PopulationInitializer populationInitializer = new BasicRandomPopulationInitializer();
		PersonInitializer personInitializer = new BasicRandomPersonInitializer();
		Mutation mutation = new BasicRandomBasedMutation();
		CrossoverMethod crossoverMethod = new BasicOnePointCrossover();
		CrossoverStrategy crossoverStrategy = new TournamentCrossoverStrategy();
		PersonTemplate personTemplate = preparePersonTemplate();
		FitnessFunction fitnessFunction = new AvgSqrFitnessFunctionForFacility();
		Selector selector = new RemoveTheWorstSelector();

		new GeneticAlgorythm(populationInitializer, personInitializer, mutation, crossoverMethod, crossoverStrategy,
				personTemplate, fitnessFunction, selector).start(NUMBER_OF_ITERATIONS);
	}

	private static PersonTemplate preparePersonTemplate() {
		List<Pair<Integer, Integer>> limits = Arrays.asList(
				new Pair<>(-100, 100),
				new Pair<>(-100, 100),
				new Pair<>(-100, 100),
				new Pair<>(-100, 100),
				new Pair<>(-100, 100),
				new Pair<>(-100, 100)
		);

		PersonTemplate template = new PersonTemplate(TEMPLATE_SIZE, limits);
		return template;
	}
}
