package mutations;

import entities.Chromosome;
import entities.Person;
import entities.PersonTemplate;
import entities.Population;

import java.util.concurrent.ThreadLocalRandom;

public class BasicRandomBasedMutation implements Mutation {
    @Override
    public void mutate(Population population, PersonTemplate template, double mutationProbability) {
        for (Person person : population.getPopulation()) {
            if (willMutate(person, mutationProbability)) {
                int chromosomeNumber = chooseChromosomeToMutate(person);
                mutateInChromosome(person, chromosomeNumber, template);

            }
        }
    }

    private void mutateInChromosome(Person person, int chromosomeNumber, PersonTemplate template) {
        Double minValue = template.getLimits().get(chromosomeNumber).first();
        Double maxValue = template.getLimits().get(chromosomeNumber).second();
        double newValueForChromosome = ThreadLocalRandom.current().nextDouble(minValue, maxValue);
        person.setChromosomeByNumber(chromosomeNumber, new Chromosome(newValueForChromosome));
    }

    private int chooseChromosomeToMutate(Person person) {
        return ThreadLocalRandom.current().nextInt(0,person.getSize());
    }

    private boolean willMutate(Person person, double mutationProbability) {
        return ThreadLocalRandom.current().nextDouble(1.0) < mutationProbability;
    }
}
