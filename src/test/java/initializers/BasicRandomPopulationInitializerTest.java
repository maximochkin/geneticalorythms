package initializers;

import entities.Chromosome;
import entities.Person;
import entities.PersonTemplate;
import entities.Population;
import org.junit.Assert;
import org.junit.Test;
import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class BasicRandomPopulationInitializerTest {

    public static final int TEMPLATE_SIZE = 5;
    public static final int POPULATION_SIZE = 5;

    @Test
    public void initializePopulationTest() {
        PersonTemplate template = prepareTemplate();
        PersonInitializer personInitializer = new BasicRandomPersonInitializer();
        Population population = new BasicRandomPopulationInitializer()
                .initialize(POPULATION_SIZE, personInitializer, template);

        for (Person person : population.getPopulation()) {
            for (int i = 0; i < person.getSize(); i++) {
                checkChromosome(person.getChromosomeByNumber(i), template.getLimits().get(i));
            }
        }
    }

    private void checkChromosome(Chromosome chromosome, Pair<Integer, Integer> limits) {
        Assert.assertTrue(chromosome.getValue() >= limits.first());
        Assert.assertTrue(chromosome.getValue() < limits.second());
    }

    private PersonTemplate prepareTemplate() {
        List<Pair<Integer, Integer>> limits = Arrays.asList(
                new Pair<>(0, 10),
                new Pair<>(5, 7),
                new Pair<>(10,20),
                new Pair<>(100, 200),
                new Pair<>(-20, -10)
        );

        PersonTemplate template = new PersonTemplate(TEMPLATE_SIZE, limits);
        return template;
    }

}
