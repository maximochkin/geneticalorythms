package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Person implements Comparable<Person> {
    private int size; //number of chromosomes
    private List<Chromosome> chromosomes;
    private double fitnessValue = 0.0;

    public Person() {
    }

    public Person(int size) {
        chromosomes = new ArrayList<>(Collections.nCopies(size, null));
        this.size = size;
    }

    public Person(int size, List<Chromosome> chromosomes) {
        this.size = size;
        this.chromosomes = chromosomes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public void setChromosomes(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public void setChromosomeByNumber(int number, Chromosome value) {
        chromosomes.set(number, value);
    }

    public Chromosome getChromosomeByNumber(int number) {
        return chromosomes.get(number);
    }

    @Override
    public int compareTo(Person o) {
        return - Double.compare(this.fitnessValue, o.fitnessValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return size == person.size &&
                Objects.equals(chromosomes, person.chromosomes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(size, chromosomes, fitnessValue);
    }

    @Override
    public String toString() {
        return "Person{" +
                "fitness=" + fitnessValue +
                " | chromosomes=" + chromosomes +
                '}';
    }

    public Person copy() {
        List<Chromosome> newChromosomes = this.getChromosomes().stream()
                .map(chromosome -> new Chromosome(chromosome.getValue()))
                .collect(Collectors.toList());

        Person newPerson = new Person(this.getSize(), newChromosomes);
        newPerson.setFitnessValue(this.getFitnessValue());
        return newPerson;
    }
}
