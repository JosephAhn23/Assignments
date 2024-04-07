import java.io.File;
import java.util.Scanner;

/**
 * @author Mehrdad Sabetzadeh, Joseph Ahn (student), University of Ottawa
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Car[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {

		if (strFilename == null) {
			System.out.println("File name cannot be null.");
			return;
		}

		// determine numRows and numSpotsPerRow; you can do so by
		// writing your own code or alternatively completing the 
		// private calculateLotDimensions(...) that I have provided
		calculateLotDimensions(strFilename);

		// instantiate the lotDesign and occupancy variables!
		// WRITE YOUR CODE HERE!
		lotDesign = new CarType [numRows][numSpotsPerRow];
		occupancy = new Car [numRows][numSpotsPerRow];

		// populate lotDesign and occupancy; you can do so by
		// writing your own code or alternatively completing the 
		// private populateFromFile(...) that I have provided
		populateFromFile(strFilename);
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
		// WRITE YOUR CODE HERE!
        if (i>=0 && i<numRows && j>=0 && j<numSpotsPerRow)
        {
            if (occupancy[i][j]==null)
            {
                occupancy[i][j]=c;
                System.out.println("Car parked at " + i + ", " + j+ " Thank you");
            }
            else{
                System.out.println("Sorry, that parking spot is already occupied. ");
            }
        }
        else{
            System.out.println("Sorry, that parking space is not available.");
        }
	}
	

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) {
		// WRITE YOUR CODE HERE!
        if (i>=0 && i<numRows && j>=0 && j<numSpotsPerRow){
            if (occupancy[i][j]==null){
                System.out.println("No car parked here");
            }
            else{
                Car removedC = occupancy[i][j];
                occupancy[i][j] = null;
                System.out.println("Car is removed from the parking spot");
                return removedC;
            }
        }
        else{
            System.out.println("Parking space is unavailable");
        }
		return null; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 *
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int i, int j, Car c) {
		// WRITE YOUR CODE HERE!
		boolean validToPark=false;
        if (i>=0 && i<numRows && j>=0 && j<numSpotsPerRow){
            if (occupancy[i][j]==null && lotDesign[i][j]!=CarType.NA){ 
				if(c.getType()==CarType.ELECTRIC){
					System.out.println("You can park here (" + i + ", "+ j+") ");
					validToPark = lotDesign[i][j]==CarType.ELECTRIC||lotDesign[i][j]==CarType.SMALL||lotDesign[i][j]==CarType.REGULAR||lotDesign[i][j]==CarType.LARGE;
				}
				else if(c.getType()==CarType.SMALL){
					System.out.println("You can park here at (" + i + ", "+ j+") ");
					validToPark = lotDesign[i][j]==CarType.SMALL||lotDesign[i][j]==CarType.REGULAR||lotDesign[i][j]==CarType.LARGE;
				}
				else if(c.getType()==CarType.REGULAR){
					System.out.println("You can park here at (" + i + ", "+ j+") ");
					validToPark = lotDesign[i][j]==CarType.REGULAR||lotDesign[i][j]==CarType.LARGE;
				}
				else if(c.getType()==CarType.LARGE){
					System.out.println("You can park here at (" + i + ", "+ j+") ");
					validToPark = lotDesign[i][j]==CarType.LARGE;
				} 
				if (validToPark){
					this.occupancy[i][j] = new Car(c.getType(), c.getPlateNum());
					System.out.println("Car:  "+ c.getType() + " "+ c.getPlateNum());
					return true;
				}
            }
            else{
                System.out.println("Sorry, currently occupied, you can't park at (" + i + ", "+ j+") ");
				System.out.println("Car: " + c.getType()+ " "+ c.getPlateNum());
			}
        }
        else{
            System.out.println("Sorry, parking space is unavailable, can't park at (" + i + ", "+ j+") ");
			System.out.println("Car: " + c.getType()+ " "+ c.getPlateNum());
		}
		return false; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		// WRITE YOUR CODE HERE!
        int totalCapacityOfParkingSpots=0; 
        for (int i=0; i<numRows; i++){
            for (int j=0; j<numSpotsPerRow; j++){
                if (lotDesign[i][j]!=CarType.NA){
                    totalCapacityOfParkingSpots++;
                }
            }
        }
		return totalCapacityOfParkingSpots; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		// WRITE YOUR CODE HERE!
        int totalOccupancyOfParkingSpots=0;
        for (int i=0; i<numRows; i++){
            for (int j=0; j<numSpotsPerRow; j++){ 
				if (occupancy[i][j]!=null){
                    totalOccupancyOfParkingSpots++;
                }
            }
        }
		return totalOccupancyOfParkingSpots; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
	}

	private void calculateLotDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));
		int numRows=0;
		int numSpotsPerRow=0;
		while (scanner.hasNextLine()) { 
			// WRITE YOUR CODE HERE!
            String line=scanner.nextLine().trim();
			
            if (line.isEmpty()){
                continue;
            }
            if(line.equals("###")){
                break;
            } 
            String[] spots = line.split(",");
            numSpotsPerRow = spots.length;
            numRows++;
		}
		scanner.close();
        System.out.println("Lot dimensions are rows: "+ numRows + ". Num of spots in rows: " + numSpotsPerRow);

		this.numRows=numRows; 
		this.numSpotsPerRow=numSpotsPerRow;
	}

	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		// YOU MAY NEED TO DEFINE SOME LOCAL VARIABLES HERE!
		
		//populate lot design
        int rowIndex=0;  
		int i=0,j=0;
		// while loop for reading the lot design
		while (scanner.hasNext()) { 
			// WRITE YOUR CODE HERE!
            String line=scanner.nextLine().trim();
            
            if (line.isEmpty()){
                continue;
            }
            if(line.equals("###")){
                break;
            }

            String[] lotDesignsArray = line.split(",");
			for (j = 0; j < numSpotsPerRow; j++) {
				this.lotDesign[i][j] = Util.getCarTypeByLabel(lotDesignsArray[j].trim());
			}
			i++;
		}

        rowIndex=0;
		// while loop for reading occupancy data
		while (scanner.hasNext()) {
			// WRITE YOUR CODE HERE!
			String line=scanner.nextLine().trim();
            if (line.isEmpty()){
                continue;
            }

            String[]data = line.split(",");


            i= Integer.parseInt(data[0].trim());
            j = Integer.parseInt(data[1].trim());
            //str -> enum val
            CarType carType = Util.getCarTypeByLabel(data[2].trim());
            String licensePlate = data[3].trim();
			
			Car sampleCar = new Car(carType, licensePlate);
			boolean parked = canParkAt(i, j, sampleCar);
		}
		scanner.close();
	}

	/**
	 * Produce string representation of the parking lot
	 *
	 * @return String containing the parking lot information
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}

		}
		return buffer.toString();
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();

		System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename); 

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println();
		
		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);

	}
}