package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Population;
import fitness.FitnessFunction;

public interface CrossoverStrategy {
    void crossover(Population population, CrossoverMethod crossoverMethod, FitnessFunction fitnessFunction);
}
