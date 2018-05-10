package initializers;

import entities.Person;
import entities.PersonTemplate;
import entities.Population;

import java.util.ArrayList;
import java.util.List;

public class BasicRandomPopulationInitializer implements PopulationInitializer {

    @Override
    public Population initialize(int size, PersonInitializer personInitializer, PersonTemplate template) {
        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < size; i ++) {
            persons.add(personInitializer.initialize(template));
        }

        Population population = new Population(size, persons);
        return population;
    }
}
