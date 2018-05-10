package initializers;

import entities.Chromosome;
import entities.Person;
import entities.PersonTemplate;
import utils.Pair;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class BasicRandomPersonInitializer implements PersonInitializer {
    public Person initialize(PersonTemplate template) {
        Person person = new Person();
        person.setSize(template.getSize());

        List<Chromosome> chromosomes = template.getLimits().stream()
                .map(this::generateRandomValue)
                .map(Chromosome::new)
                .collect(Collectors.toList());
        person.setChromosomes(chromosomes);

        return person;
    }

    private int generateRandomValue(Pair<Integer, Integer> limits) {
        return ThreadLocalRandom.current().nextInt(limits.first(), limits.second());
    }
}
