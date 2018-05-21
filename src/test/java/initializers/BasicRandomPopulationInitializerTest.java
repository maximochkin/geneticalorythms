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

    private void checkChromosome(Chromosome chromosome, Pair<Double, Double> limits) {
        Assert.assertTrue(chromosome.getValue() >= limits.first());
        Assert.assertTrue(chromosome.getValue() < limits.second());
    }

    private PersonTemplate prepareTemplate() {
        List<Pair<Double, Double>> limits = Arrays.asList(
                new Pair<>(0.0d, 10.0d),
                new Pair<>(5.0d, 7.0d),
                new Pair<>(10.0d, 20.0d),
                new Pair<>(100.0d, 200.0d),
                new Pair<>(-20.0d, -10.0d)
        );

        PersonTemplate template = new PersonTemplate(TEMPLATE_SIZE, limits);
        return template;
    }

}
