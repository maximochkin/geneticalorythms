package selection;

import entities.Person;
import entities.Population;
import utils.Roulette;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MGGSelection implements Selector {
    @Override
    public void select(Population population, int maxSizeOfPopulation, List<Person> children) {
        List<Person> family = new ArrayList<>();
        for (int i = 0; i < population.getSize() - 1; i+=2) {
            family.clear();
            family.add(population.getPersonByNumber(i));
            family.add(population.getPersonByNumber(i+1));
            family.add(children.get(i));
            family.add(children.get(i+1));
            modifyPopulation(population, family, i);
        }
    }

    private void modifyPopulation(Population population, List<Person> family, int position) {
        Person survivor1 = family.stream()
                .max(Comparator.comparingDouble(Person::getFitnessValue))
                .get();     //no need for orElse(...)

        family.remove(survivor1);
        Person survivor2 = Roulette.pickPersonByRouletteWheel(family, null);
        population.getPopulation().set(position, survivor1);
        population.getPopulation().set(position+1, survivor2);
    }
}
