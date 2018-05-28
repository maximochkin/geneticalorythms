package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Person;
import entities.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TournamentCrossoverStrategy implements CrossoverStrategy {
    @Override
    public List<Person> crossover(Population population, CrossoverMethod crossoverMethod, double crossoverProbability) {
        Collections.shuffle(population.getPopulation());
        List<Person> parentsPool = new ArrayList<>();
        for (int i = 0; i < population.getSize() - 1; i += 2) {
            if (willCrossover(crossoverProbability)) {
                if (Double.compare(population.getPersonByNumber(i).getFitnessValue(), population.getPersonByNumber(i + 1).getFitnessValue()) > 0) {
                    parentsPool.add(population.getPersonByNumber(i));
                } else {
                    parentsPool.add(population.getPersonByNumber(i + 1));
                }
            }
        }

        List<Person> children = new ArrayList<>();
        for (int i = 0; i < parentsPool.size() - 1; i+=2) {
            children.addAll(crossoverMethod.crossover(parentsPool.get(i), parentsPool.get(i + 1)));
        }

        return children;
    }
}
