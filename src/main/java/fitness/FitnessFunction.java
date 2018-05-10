package fitness;

import entities.Person;

import java.util.function.Function;

public interface FitnessFunction {
    double apply(Person person);
}
