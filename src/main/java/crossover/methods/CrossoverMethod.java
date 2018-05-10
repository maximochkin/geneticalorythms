package crossover.methods;

import entities.Person;
import entities.Population;

import java.util.List;

public interface CrossoverMethod {
    List<Person> crossover(Person firstParent, Person secondParent);
}
