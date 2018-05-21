package fitness;

import entities.Chromosome;
import entities.Person;

public class ForTestingFitnessFunction implements FitnessFunction {

    public double apply(Person person) {
        double fitness = (1 / person.getChromosomes().stream().mapToDouble(Chromosome::getValue).sum());
        person.setFitnessValue(fitness);
        return fitness;
    }
}
