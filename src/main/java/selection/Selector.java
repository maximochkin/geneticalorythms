package selection;

import entities.Population;

public interface Selector {
    void select(Population population, int maxSizeOfPopulation, int numberOfChildren);
}
