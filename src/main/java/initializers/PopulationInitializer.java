package initializers;

import entities.PersonTemplate;
import entities.Population;

public interface PopulationInitializer {
    Population initialize(int size, PersonInitializer personInitializer, PersonTemplate template);
}
