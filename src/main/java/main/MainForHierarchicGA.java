package main;

import algorythm.GeneticAlgorithm;
import crossover.methods.BLXAlphaCrossoverMethod;
import crossover.methods.CrossoverMethod;
import crossover.strategies.CrossoverStrategy;
import crossover.strategies.RandomParentsCrossoverStrategy;
import entities.Person;
import entities.PersonTemplate;
import fitness.FitnessForHierarchy1SubModel;
import fitness.FitnessForHierarchy2SubModel;
import fitness.FitnessForHierarchy3SubModel;
import fitness.FitnessHierarchyFinalModel;
import initializers.BasicRandomPersonInitializer;
import initializers.BasicRandomPopulationInitializer;
import initializers.PersonInitializer;
import initializers.PopulationInitializer;
import mutations.BasicRandomBasedMutation;
import mutations.Mutation;
import selection.MGGSelection;
import selection.Selector;
import utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainForHierarchicGA {

	private static final int TEMPLATE_SIZE = 7;

	public static void main(String[] args) {
		PopulationInitializer populationInitializer = new BasicRandomPopulationInitializer();
		PersonInitializer personInitializer = new BasicRandomPersonInitializer();
		Mutation mutation = new BasicRandomBasedMutation();
		CrossoverMethod crossoverMethod = new BLXAlphaCrossoverMethod();
		CrossoverStrategy crossoverStrategy = new RandomParentsCrossoverStrategy();
		Selector selector = new MGGSelection();

		PersonTemplate personTemplateForPrimaryModel = new PersonTemplate(3, Collections.nCopies(3, new Pair<>(-100.0d, 100.0d)));
		PersonTemplate personTemplateForSecondaryModel = new PersonTemplate(4, Collections.nCopies(4, new Pair<>(-100.0d, 100.0d)));

		Person bestOfTheBest1 = new GeneticAlgorithm(populationInitializer, personInitializer, mutation, crossoverMethod, crossoverStrategy,
				personTemplateForPrimaryModel, new FitnessForHierarchy1SubModel(), selector).start();

		Person bestOfTheBest2 = new GeneticAlgorithm(populationInitializer, personInitializer, mutation, crossoverMethod, crossoverStrategy,
				personTemplateForPrimaryModel, new FitnessForHierarchy2SubModel(), selector).start();

		Person bestOfTheBest3 = new GeneticAlgorithm(populationInitializer, personInitializer, mutation, crossoverMethod, crossoverStrategy,
				personTemplateForPrimaryModel, new FitnessForHierarchy3SubModel(), selector).start();

		FitnessHierarchyFinalModel fitnessFinal = new FitnessHierarchyFinalModel();
		List<Double> coeffs = new ArrayList<>();

		bestOfTheBest1.getChromosomes().forEach(chromosome -> coeffs.add(chromosome.getValue()));
		bestOfTheBest2.getChromosomes().forEach(chromosome -> coeffs.add(chromosome.getValue()));
		bestOfTheBest3.getChromosomes().forEach(chromosome -> coeffs.add(chromosome.getValue()));

		fitnessFinal.setCoefficientsFromSubModels(coeffs);

		Person bestOfTheBest = new GeneticAlgorithm(populationInitializer, personInitializer, mutation, crossoverMethod, crossoverStrategy,
				personTemplateForSecondaryModel, fitnessFinal, selector).start();

		System.out.println(bestOfTheBest.toString());
	}
}
