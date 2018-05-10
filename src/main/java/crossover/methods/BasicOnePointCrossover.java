package crossover.methods;

import entities.Person;
import entities.Population;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BasicOnePointCrossover implements CrossoverMethod {

    @Override
    public List<Person> crossover(Person firstParent, Person secondParent) {
        int pointPosition = getPointPosition(firstParent.getSize());
        Person firstChild = new Person(firstParent.getSize());
        Person secondChild = new Person(firstParent.getSize());
        for (int i = 0; i < firstChild.getSize(); i++) {
            if (i < pointPosition) {
                firstChild.setChromosomeByNumber(i, firstParent.getChromosomeByNumber(i));
                secondChild.setChromosomeByNumber(i, secondParent.getChromosomeByNumber(i));
            } else {
                firstChild.setChromosomeByNumber(i, secondParent.getChromosomeByNumber(i));
                secondChild.setChromosomeByNumber(i, firstParent.getChromosomeByNumber(i));
            }
        }
        return Arrays.asList(firstChild, secondChild);
    }

    int getPointPosition(int size) {
        return ThreadLocalRandom.current().nextInt(1, size);
    }
}
