package crossover.methods;

import entities.Person;
import entities.PersonTemplate;
import initializers.BasicRandomPersonInitializer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class BasicOnePointCrossoverTest {

    public static final int SIZE = 5;
    public static final int POINT_POSITION = 3;

    @Test
    public void crossoverTest() {
        BasicOnePointCrossover crossover = new BasicOnePointCrossover();
        BasicOnePointCrossover crossoverSpy = Mockito.spy(crossover);

        Mockito.doReturn(POINT_POSITION).when(crossoverSpy).getPointPosition(SIZE);

        Person parent1 = new BasicRandomPersonInitializer().initialize(preparePersonTemplate());
        Person parent2 = new BasicRandomPersonInitializer().initialize(preparePersonTemplate());

        List<Person> children = crossoverSpy.crossover(parent1, parent2);
        Person child1 = children.get(0);
        Person child2 = children.get(1);

        for (int i = 0; i < child1.getSize(); i++) {
            if (i < POINT_POSITION) {
                Assert.assertEquals(child1.getChromosomeByNumber(i), parent1.getChromosomeByNumber(i));
                Assert.assertEquals(child2.getChromosomeByNumber(i), parent2.getChromosomeByNumber(i));
            } else {
                Assert.assertEquals(child1.getChromosomeByNumber(i), parent2.getChromosomeByNumber(i));
                Assert.assertEquals(child2.getChromosomeByNumber(i), parent1.getChromosomeByNumber(i));
            }
        }

    }

    private PersonTemplate preparePersonTemplate() {
        List<Pair<Double, Double>> limits = Arrays.asList(
                new Pair<>(0.0d, 10.0d),
                new Pair<>(5.0d, 7.0d),
                new Pair<>(10.0d,20.0d),
                new Pair<>(100.0d, 200.0d),
                new Pair<>(-20.0d, -10.0d)
        );

        PersonTemplate template = new PersonTemplate(SIZE, limits);
        return template;
    }

}
