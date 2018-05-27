package selection;

import entities.Person;
import entities.Population;

import java.util.List;

public interface Selector {
    void select(Population population, int maxSizeOfPopulation, List<Person> children);
}
