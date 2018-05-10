package initializers;

import entities.Person;
import entities.PersonTemplate;

public interface PersonInitializer {
    Person initialize(PersonTemplate template);
}
