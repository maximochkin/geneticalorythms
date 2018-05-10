package crossover.strategies;

import entities.Chromosome;
import entities.Person;
import entities.Population;
import fitness.AvgSqrFitnessFunction;
import fitness.FitnessFunction;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class RouletteCrossoverStrategyTest {
	public static final double RND = 0.03;
	public static final int TEMPLATE_SIZE = 3;
	public static final int POPULATION_SIZE = 3;

	@Test
	public void getParentTest() {
		RouletteCrossoverStrategy crossoverStrategy = new RouletteCrossoverStrategy();
		FitnessFunction fitnessFunction = new AvgSqrFitnessFunction();
		RouletteCrossoverStrategy crossoverStrategySpy = Mockito.spy(crossoverStrategy);
		Population population = preparePopulation();
		calculateFitness(population, fitnessFunction);
		double sumWeight = population.getPopulation().stream().mapToDouble(Person::getFitnessValue).sum();
		Mockito.doReturn(RND).when(crossoverStrategySpy).getRandomWeight(sumWeight);
		Person pickedPerson = crossoverStrategySpy.getParent(population, sumWeight, null);
		Assert.assertEquals(population.getPersonByNumber(0), pickedPerson);
	}

	private void calculateFitness(Population population, FitnessFunction fitnessFunction) {
		population.getPopulation().forEach(fitnessFunction::apply);
	}

	private Population preparePopulation() {
		List<Person> persons = Arrays.asList(
				new Person(TEMPLATE_SIZE, Arrays.asList(new Chromosome(1), new Chromosome(2), new Chromosome(3))),
				new Person(TEMPLATE_SIZE, Arrays.asList(new Chromosome(3), new Chromosome(4), new Chromosome(2))),
				new Person(TEMPLATE_SIZE, Arrays.asList(new Chromosome(5), new Chromosome(1), new Chromosome(1)))
		);
		Population population = new Population(POPULATION_SIZE, persons);
		return population;

	}
}
