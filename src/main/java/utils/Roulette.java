package utils;

import entities.Person;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Roulette {

    // 3rd parameter is for not to pick one person two times
    public static Person pickPersonByRouletteWheel(List<Person> persons, Person exception) {
        Optional<Integer> positionOfException;
        if (exception != null) {
            positionOfException = Optional.of(persons.indexOf(exception));
            persons.remove(positionOfException.get().intValue());
        } else {
            positionOfException = Optional.empty();
        }

        double sumWeight = persons.stream().mapToDouble(Person::getFitnessValue).sum();

        List<Double> fitnessValues = persons.stream()
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
                    if (!persons.get(i).equals(exception)) {
                        parentIndex = i;
                        correctHasBeenFound = true;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        Person personToReturn = persons.get(parentIndex);

        positionOfException.ifPresent(integer -> persons.add(integer, exception));

        return personToReturn;
    }

    private static double getRandomWeight(double sumWeight) {
        return ThreadLocalRandom.current().nextDouble(sumWeight);
    }
}
