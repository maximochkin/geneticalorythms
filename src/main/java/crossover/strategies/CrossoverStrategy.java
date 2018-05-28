package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Person;
import entities.Population;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public interface CrossoverStrategy {
    List<Person> crossover(Population population, CrossoverMethod crossoverMethod, double crossoverProbability);

    default boolean willCrossover(double crossoverProbability) {
        return Double.compare(ThreadLocalRandom.current().nextDouble(0.0d, 1.0d), crossoverProbability) <= 0;
    }
}
