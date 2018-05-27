package main;

import algorythm.GeneticAlgorythm;
import crossover.methods.BLXAlphaCrossoverMethod;
import crossover.methods.BasicOnePointCrossover;
import crossover.methods.CrossoverMethod;
import crossover.strategies.CrossoverStrategy;
import crossover.strategies.RandomParentsCrossoverStrategy;
import crossover.strategies.RouletteCrossoverStrategy;
import crossover.strategies.TournamentCrossoverStrategy;
import entities.Person;
import entities.PersonTemplate;
import fitness.AvgSqrFitnessFunctionForFacility;
import fitness.FitnessFunction;
import initializers.BasicRandomPersonInitializer;
import initializers.BasicRandomPopulationInitializer;
import initializers.PersonInitializer;
import initializers.PopulationInitializer;
import mutations.BasicRandomBasedMutation;
import mutations.Mutation;
import selection.MGGSelection;
import selection.RemoveTheWorstSelector;
import selection.Selector;
import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class Main {

	private static final int TEMPLATE_SIZE = 7;

	public static void main(String[] args) {
		PopulationInitializer populationInitializer = new BasicRandomPopulationInitializer();
		PersonInitializer personInitializer = new BasicRandomPersonInitializer();
		Mutation mutation = new BasicRandomBasedMutation();
		CrossoverMethod crossoverMethod = new BasicOnePointCrossover();
		CrossoverStrategy crossoverStrategy = new RandomParentsCrossoverStrategy();
		PersonTemplate personTemplate = preparePersonTemplate();
		FitnessFunction fitnessFunction = new AvgSqrFitnessFunctionForFacility();
		Selector selector = new MGGSelection();

		Person bestOfTheBest = new GeneticAlgorythm(populationInitializer, personInitializer, mutation, crossoverMethod, crossoverStrategy,
				personTemplate, fitnessFunction, selector).start();

		System.out.println(bestOfTheBest.toString());
	}

	private static PersonTemplate preparePersonTemplate() {
		List<Pair<Double, Double>> limits = Arrays.asList(
				new Pair<>(-100.0d, 100.0d),
				new Pair<>(-100.0d, 100.0d),
				new Pair<>(-100.0d, 100.0d),
				new Pair<>(-100.0d, 100.0d),
				new Pair<>(-100.0d, 100.0d),
				new Pair<>(-100.0d, 100.0d),
				new Pair<>(-100.0d, 100.0d)
		);

		PersonTemplate template = new PersonTemplate(TEMPLATE_SIZE, limits);
		return template;
	}
}
