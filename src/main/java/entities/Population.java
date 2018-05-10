package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
    int size; //number of persons in population
    List<Person> population;

    public Population(int size) {
        population = new ArrayList<>(Collections.nCopies(size, null));
        this.size = size;
    }

    public Population(int size, List<Person> population) {
        this.size = size;
        this.population = population;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Person> getPopulation() {
        return population;
    }

    public void setPopulation(List<Person> population) {
        this.population = population;
    }

    public void setPersonByNumber(int number, Person value) {
        population.set(number, value);
    }

    public Person getPersonByNumber(int number) {
        return population.get(number);
    }

    public void sort() {
        Collections.sort(population);
    }


}
