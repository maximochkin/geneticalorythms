package entities;

import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class PersonTemplate {
    int size; //number of chromosomes
    List<Pair<Double, Double>> limits; // min and max value for every chromosome

    public PersonTemplate() {
    }

    public PersonTemplate(int size) {
        this.size = size;
        limits = new ArrayList<>(size);
    }

    public PersonTemplate(int size, List<Pair<Double, Double>> limits) {
        this.size = size;
        this.limits = limits;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Pair<Double, Double>> getLimits() {
        return limits;
    }

    public void setLimits(List<Pair<Double, Double>> limits) {
        this.limits = limits;
    }
}
