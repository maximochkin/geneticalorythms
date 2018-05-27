package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Person;
import entities.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomParentsCrossoverStrategy implements CrossoverStrategy {
    @Override
    public List<Person> crossover(Population population, CrossoverMethod crossoverMethod) {
        Collections.shuffle(population.getPopulation());

        List<Person> children = new ArrayList<>();
        List<Person> parents = population.getPopulation();
        for (int i = 0; i < parents.size() - 1; i+=2) {
            children.addAll(crossoverMethod.crossover(parents.get(i), parents.get(i + 1)));
        }

        return children;
    }
}
