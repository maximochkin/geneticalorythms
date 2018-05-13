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
    public List<Person> crossover(Population population, CrossoverMethod crossoverMethod) {
        double sumWeight = population.getPopulation().stream().mapToDouble(Person::getFitnessValue).sum();
        List<Person> children = new ArrayList<>();
        for (int i = 0; i < population.getSize() / 2; i++) {
            Person parent1 = getParent(population, sumWeight, null);
            Person parent2 = getParent(population, sumWeight, parent1);
            children.addAll(crossoverMethod.crossover(parent1, parent2));
        }
        return children;
    }

    // 3rd parameter is for not to pick one person two times
    Person getParent(Population population, double sumWeight, Person exception) {

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
