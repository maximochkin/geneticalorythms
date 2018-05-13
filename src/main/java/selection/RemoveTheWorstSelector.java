package selection;

import entities.Population;

import java.util.stream.Collectors;

public class RemoveTheWorstSelector implements Selector {
    @Override
    public void select(Population population, int maxSizeOfPopulation, int numberOfChildren) {
        population.sort();
        if (population.getSize() > maxSizeOfPopulation) {
            population.setPopulation(population.getPopulation().stream()
                    .sorted().limit(maxSizeOfPopulation).collect(Collectors.toList()));
            population.setSize(population.getPopulation().size());
        }
    }
}
