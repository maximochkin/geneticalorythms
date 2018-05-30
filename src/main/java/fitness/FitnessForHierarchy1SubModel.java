package fitness;

import entities.Person;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FitnessForHierarchy1SubModel implements FitnessFunction {
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
						person.getChromosomeByNumber(1).getValue() * Hi.get(i) +
						person.getChromosomeByNumber(2).getValue()), 2.0);
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
		scanner.close();
	}
}
