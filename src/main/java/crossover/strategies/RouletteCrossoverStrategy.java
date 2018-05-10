package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Person;
import entities.Population;
import fitness.FitnessFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RouletteCrossoverStrategy implements CrossoverStrategy {
    @Override
    public void crossover(Population population, CrossoverMethod crossoverMethod, FitnessFunction fitnessFunction) {
        population.sort();
        List<Person> children = new ArrayList<>();
        for (int i = 0; i < population.getSize() / 2; i++) {
            Person parent1 = getParent(population, null);
            Person parent2 = getParent(population, parent1);
            children.addAll(crossoverMethod.crossover(parent1, parent2));
        }

        children.stream().forEach(fitnessFunction::apply);
        population.add(children);
    }

    // 3rd parameter is for not to pick up one person two times
    Person getParent(Population population, Person exception) {
        double sumWeight = population.getPopulation().stream().mapToDouble(Person::getFitnessValue).sum();

        List<Double> fitnessValues = population.getPopulation().stream()
                .map(Person::getFitnessValue).collect(Collectors.toList());

        List<Double> weights = new ArrayList<>(Collections.nCopies(fitnessValues.size(), null));
        weights.set(0, fitnessValues.get(0));

        for (int i = 1; i < weights.size(); i++) {
            weights.set(i, weights.get(i-1) + fitnessValues.get(i));
        }

        boolean correctHasBeenFound = false;
        double rnd = 0.0;
        int parentIndex = 0;

        while (!correctHasBeenFound) {
            rnd = getRandomWeight(sumWeight);
            for (int i = 0; i < weights.size(); i++) {
                if (rnd < weights.get(i)) {
                    if (!population.getPersonByNumber(i).equals(exception)) {
                        parentIndex = i;
                        correctHasBeenFound = true;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        return population.getPersonByNumber(parentIndex);
    }

    double getRandomWeight(double sumWeight) {
        return ThreadLocalRandom.current().nextDouble(sumWeight);
    }
}
