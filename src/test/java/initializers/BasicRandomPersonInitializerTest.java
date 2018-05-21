package initializers;

import entities.Chromosome;
import entities.Person;
import entities.PersonTemplate;
import org.junit.Assert;
import org.junit.Test;
import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class BasicRandomPersonInitializerTest {

    public static final int TEMPLATE_SIZE = 5;

    @Test
    public void generatePersonTest() {
        PersonTemplate template = preparePersonTemplate();
        Person generatedPerson = new BasicRandomPersonInitializer().initialize(template);
        for (int i = 0; i < template.getSize(); i ++) {
            checkChromosome(generatedPerson.getChromosomeByNumber(i), template.getLimits().get(i));
        }
    }

    private void checkChromosome(Chromosome chromosome, Pair<Double, Double> limits) {
        Assert.assertTrue(chromosome.getValue() >= limits.first());
        Assert.assertTrue(chromosome.getValue() < limits.second());
    }

    private PersonTemplate preparePersonTemplate() {
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
