package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Person;
import entities.Population;
import fitness.FitnessFunction;

import java.util.List;

public interface CrossoverStrategy {
    List<Person> crossover(Population population, CrossoverMethod crossoverMethod);
}
