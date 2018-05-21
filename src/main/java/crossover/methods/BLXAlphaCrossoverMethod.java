package crossover.methods;

import entities.Chromosome;
import entities.Person;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BLXAlphaCrossoverMethod implements CrossoverMethod {

    private static final double ALPHA = 0.5d;

    @Override
    public List<Person> crossover(Person firstParent, Person secondParent) {
        Person child1 = new Person(firstParent.getSize());
        Person child2 = new Person(firstParent.getSize());
        for (int i = 0; i < firstParent.getSize(); i++) {
            double valueOnPositionParent1 = firstParent.getChromosomeByNumber(i).getValue();
            double valueOnPositionParent2 = secondParent.getChromosomeByNumber(i).getValue();
            double d = Math.abs(valueOnPositionParent1 - valueOnPositionParent2);

            double newValueForFirstChild = getRandomDoubleFromRange(
                    Math.min(valueOnPositionParent1, valueOnPositionParent2) - ALPHA * d,
                    Math.max(valueOnPositionParent1, valueOnPositionParent2) + ALPHA * d
            );
            child1.setChromosomeByNumber(i, new Chromosome(newValueForFirstChild));

            double newValueForSecondChild = getRandomDoubleFromRange(
                    Math.min(valueOnPositionParent1, valueOnPositionParent2) - ALPHA * d,
                    Math.max(valueOnPositionParent1, valueOnPositionParent2) + ALPHA * d
            );
            child2.setChromosomeByNumber(i, new Chromosome(newValueForSecondChild));
        }
        return Arrays.asList(child1, child2);
    }

    private double getRandomDoubleFromRange(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}
