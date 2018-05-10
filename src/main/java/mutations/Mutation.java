package mutations;

import entities.Person;
import entities.PersonTemplate;
import entities.Population;

public interface Mutation {
    void mutate(Population population, PersonTemplate template, double mutationProbability);
}
