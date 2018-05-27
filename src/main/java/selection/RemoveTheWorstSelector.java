package selection;

import entities.Person;
import entities.Population;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveTheWorstSelector implements Selector {
    @Override
    public void select(Population population, int maxSizeOfPopulation, List<Person> children) {
        population.add(children);
        population.sort();
        if (population.getSize() > maxSizeOfPopulation) {
            population.setPopulation(population.getPopulation().stream()
                    .sorted().limit(maxSizeOfPopulation).collect(Collectors.toList()));
            population.setSize(population.getPopulation().size());
        }
    }
}
