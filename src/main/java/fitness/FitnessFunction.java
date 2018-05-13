package fitness;

import entities.Person;

public interface FitnessFunction {
    double apply(Person person);
}
