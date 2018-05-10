package fitness;

import entities.Chromosome;
import entities.Person;

import java.util.stream.Collectors;

public class AvgSqrFitnessFunction implements FitnessFunction {

    public double apply(Person person) {
        return (double) person.getChromosomes().stream().mapToInt(Chromosome::getValue).sum();
    }
}
