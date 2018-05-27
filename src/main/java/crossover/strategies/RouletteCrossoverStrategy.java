package crossover.strategies;

import crossover.methods.CrossoverMethod;
import utils.Roulette;
import entities.Person;
import entities.Population;

import java.util.ArrayList;
import java.util.List;

public class RouletteCrossoverStrategy implements CrossoverStrategy {
    @Override
    public List<Person> crossover(Population population, CrossoverMethod crossoverMethod) {
        List<Person> children = new ArrayList<>();
        for (int i = 0; i < population.getSize() / 2; i++) {
            Person parent1 = Roulette.pickPersonByRouletteWheel(population.getPopulation(), null);
            Person parent2 = Roulette.pickPersonByRouletteWheel(population.getPopulation(), parent1);
            children.addAll(crossoverMethod.crossover(parent1, parent2));
        }
        return children;
    }
}
