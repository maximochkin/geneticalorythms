package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Population;

public interface CrossoverStrategy {
    void crossover(Population population, CrossoverMethod crossoverMethod);
}
