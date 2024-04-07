import java.util.ArrayList;
import java.util.List;

public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
        int optimalSpots = 1;
        double minAvgQueueLength = Double.MAX_VALUE;

        for (int numSpots = 1; numSpots <= 1000; numSpots++) {
            double lengthAvg = simulateParkingLot(numSpots, hourlyRate);
            if (lengthAvg == -1) {
                break;
            }

            if (lengthAvg < minAvgQueueLength) {
                minAvgQueueLength = lengthAvg;
                optimalSpots = numSpots;
            }
        }

        return optimalSpots;
    }


    private static double simulateParkingLot(int numSpots, int hourlyRate) {
        double length = 0;
        int zeroQueueCount = 0;
        List<String> simulationResults = new ArrayList<>();

        for (int run = 1; run <= NUM_RUNS; run++) {
            Simulator simulator = new Simulator(new ParkingLot(numSpots), hourlyRate, 24 * Simulator.NUM_SECONDS_IN_1H);
            simulator.simulate();
            length += simulator.getIncomingQueueSize();
            simulationResults.add("Simulation run " + run + " ; Queue length at the end of simulation run: " + simulator.getIncomingQueueSize() );
			
            if (simulator.getIncomingQueueSize() == 0) {
                zeroQueueCount++;
            }

            if (zeroQueueCount >= 5) {
                simulationResults.add("Exiting the simulation.");
				for (String result : simulationResults) {
					System.out.println(result);
				}
                return -1;
            }
        }
        System.out.println("==== Setting lot capacity to: " + numSpots + " ====");
        for (String result : simulationResults) {
			System.out.println(result);
		}

        return length / NUM_RUNS;
    }

	
	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	} 
	
	
}