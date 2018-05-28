package crossover.strategies;

import crossover.methods.CrossoverMethod;
import entities.Chromosome;
import entities.Person;
import entities.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomParentsCrossoverStrategy implements CrossoverStrategy {
    @Override
    public List<Person> crossover(Population population, CrossoverMethod crossoverMethod, double crossoverProbability) {
        Collections.shuffle(population.getPopulation());

        List<Person> children = new ArrayList<>();
        List<Person> parents = population.getPopulation();
        int sizeOfPerson = parents.get(0).getSize();

        for (int i = 0; i < parents.size() - 1; i+=2) {
            if (willCrossover(crossoverProbability)) {
                children.addAll(crossoverMethod.crossover(parents.get(i), parents.get(i + 1)));
            } else { // if parents don't crossover we will add two dummy persons in the list of children to be removed by MGG
                children.add(new Person(sizeOfPerson, new ArrayList<>(Collections.nCopies(sizeOfPerson, new Chromosome(0)))));
                children.add(new Person(sizeOfPerson, new ArrayList<>(Collections.nCopies(sizeOfPerson, new Chromosome(0)))));
            }
        }

        return children;
    }
}
