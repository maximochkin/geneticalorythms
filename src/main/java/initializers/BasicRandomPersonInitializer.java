package initializers;

import entities.Chromosome;
import entities.Person;
import entities.PersonTemplate;
import utils.Pair;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
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

    private Double generateRandomValue(Pair<Double, Double> limits) {
        return ThreadLocalRandom.current().nextDouble(limits.first(), limits.second());
    }
}
