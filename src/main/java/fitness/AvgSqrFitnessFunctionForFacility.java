package fitness;

import entities.Person;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AvgSqrFitnessFunctionForFacility implements FitnessFunction {
//    int[] Ni    = new int[] {5,8,9,10,10,10,10,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,11,11,11,10,10,10,11,11,11,11,12,12,12,12,11,11,11,11};
//    int[] Ni1   = new int[] {5,5,8,9,10,10,10,10,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,11,11,11,10,10,10,11,11,11,11,12,12,12,12,11,11,11};
//    int[] Vi    = new int[] {790,792,794,796,798,799,801,806,807,809,810,812,813,817,819,822,824,825,827,828,829,831,834,836,838,841,842,844,845,847,849,850,851,854,855,857,859,861,863,866};
//    int[] Vi1   = new int[] {790,790,792,794,796,798,799,801,806,807,809,810,812,813,817,819,822,824,825,827,828,829,831,834,836,838,841,842,844,845,847,849,850,851,854,855,857,859,861,863};
//    double[] Hi = new double[] {1.81,1.83,1.85,1.82,1.82, };

    private static List<Integer> Ni = new ArrayList<>();
    private static List<Integer> Ni1 = new ArrayList<>();
    private static List<Integer> Vi = new ArrayList<>();
    private static List<Integer> Vi1 = new ArrayList<>();
    private static List<Double> Hi = new ArrayList<>();
    private static List<Integer> Tkp = new ArrayList<>();
    private static List<Integer> Tsm = new ArrayList<>();


    public static final int NUMBER_OF_ROWS = 40;

    @Override
    public double apply(Person person) {
        double fitnessValue = 0.0;
        try {
            if (Ni.isEmpty()) {
                init();
            }

            if (person.getChromosomes().stream().allMatch(chromosome -> Double.compare(chromosome.getValue(), 0.0d) == 0)) {
                return 0.0d;
            }

            for (int i = 0; i < NUMBER_OF_ROWS; i++) {
                fitnessValue += Math.pow(Tsm.get(i) - (person.getChromosomeByNumber(0).getValue() * Ni.get(i) +
                        person.getChromosomeByNumber(1).getValue() * Ni1.get(i) +
                        person.getChromosomeByNumber(2).getValue() * Vi.get(i) +
                        person.getChromosomeByNumber(3).getValue() * Vi1.get(i) +
                        person.getChromosomeByNumber(4).getValue() * Hi.get(i) +
                        person.getChromosomeByNumber(5).getValue() * Tkp.get(i) +
                        person.getChromosomeByNumber(6).getValue()), 2.0);
            }
            fitnessValue = NUMBER_OF_ROWS / fitnessValue;
            fitnessValue = Math.sqrt(fitnessValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        person.setFitnessValue(fitnessValue);
        return fitnessValue;
    }

    public void init() throws IOException {
        Path filePath = Paths.get("input-data-for-fitness-function.txt");
        Scanner scanner = new Scanner(filePath);
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            Ni.add(scanner.nextInt());
            Ni1.add(scanner.nextInt());
            Vi.add(scanner.nextInt());
            Vi1.add(scanner.nextInt());
            Hi.add(scanner.nextDouble());
            Tkp.add(scanner.nextInt());
            Tsm.add(scanner.nextInt());
        }
    }
}
