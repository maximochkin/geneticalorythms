package entities;

import java.util.Objects;

public class Chromosome {
    private double value;

    public Chromosome() {
    }

    public Chromosome(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}
