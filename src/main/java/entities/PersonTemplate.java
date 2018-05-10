package entities;

import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class PersonTemplate {
    int size; //number of chromosomes
    List<Pair<Integer, Integer>> limits; // min and max value for every chromosome

    public PersonTemplate() {
    }

    public PersonTemplate(int size) {
        this.size = size;
        limits = new ArrayList<Pair<Integer, Integer>>(size);
    }

    public PersonTemplate(int size, List<Pair<Integer, Integer>> limits) {
        this.size = size;
        this.limits = limits;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Pair<Integer, Integer>> getLimits() {
        return limits;
    }

    public void setLimits(List<Pair<Integer, Integer>> limits) {
        this.limits = limits;
    }
}
