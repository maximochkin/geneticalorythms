package fitness;

import entities.Chromosome;
import entities.Person;

public class ForTestingFitnessFunction implements FitnessFunction {

    public double apply(Person person) {
        double fitness = (1 / (double) person.getChromosomes().stream().mapToInt(Chromosome::getValue).sum());
        person.setFitnessValue(fitness);
        return fitness;
    }
}
