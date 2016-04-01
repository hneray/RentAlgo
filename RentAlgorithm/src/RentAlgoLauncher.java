import java.util.Arrays;
import java.util.Scanner;


public class RentAlgoLauncher {
	// numRenters will = numRooms
	private static final int numRenters = 3;

	// current owners of the rooms, indexed by room.
	// e.g., roomOwners[0] contains the index of the player who owns room 0
	private static int[] roomOwners = new int[numRenters];

	// Note: Could use a 2d array with rooms in the first dimension
	// and their ownership status and bids in the second
	// but decided to use Player objects and a roomOwners array for
	// readability and extensibility
	private static Renter[] renters = new Renter[numRenters];

	// maximum "envy" value
	private static int epsilon;

	// total rent required for the entire apartment
	private static int totalRent;
	
	private static final String welcomeMessage = "Welcome to the Rent Division Algorithm!";

	public static void main(String[] args) {

		// set each room to have no owner
		Arrays.fill(roomOwners, -1);

		welcomeMessage();
		
		// we will read the input from the renters
		Scanner scanner = new Scanner(System.in);
		
		saveTotalRent(scanner);

		saveEpsilon(scanner);

		saveNamesAndBids(scanner);

		scanner.close();
		
		RentAlgo rentAlgo = new RentAlgo(roomOwners, renters, epsilon, totalRent);

		rentAlgo.runAuction();

		// print out how much each person ultimately bid for their room
		int totalPaid = rentAlgo.calculateTotalPayment();
		// redistribute any extra cash evenly
		rentAlgo.redistribute(totalPaid);

	}

	public static void welcomeMessage(){
		System.out.println(welcomeMessage);
	}
	
	
	public static void saveTotalRent(Scanner scanner) {
		
		totalRent = 0;
		while (totalRent <= 0) {
			System.out.println("Please enter a valid total rent. Must be an integer > 0 ");
			while (!scanner.hasNextInt()){
				System.out.println("That's not an integer! Please enter a valid rent!");
				scanner.next();
			}
			totalRent = scanner.nextInt();
			scanner.nextLine();
		}
	}

	
	//since we're dealing with dollars, we'll keep epsilon to integer values for now
	public static void saveEpsilon(Scanner scanner) {
		// error check for string

		// epsilon must be > 0
		// but it's max could be altered depending on desires of participants
		// a lower max -> more refined result but longer runtime
		//just for testing:totalRent = 1000;

		//keep as double? round down?
		int maxEpsilon = totalRent / 20;

		epsilon = 0;

		while (epsilon <= 0 || epsilon > maxEpsilon) {
			System.out.println("Please enter epsilon (measure of envy). Must be an integer > 0 and <= " + maxEpsilon
					);
			while (!scanner.hasNextInt()){
				System.out.println("That's not an integer! Please enter a valid epsilon!");
				scanner.next();
			}
			
			epsilon = scanner.nextInt();
			scanner.nextLine();
		}

	}

	public static void saveNamesAndBids(Scanner scanner) {
		String name = ""; // could make this stringbuilder?

		// ask each renter for his/her name and his/her initial bids
		for (int i = 0; i < numRenters; i++) {
			System.out.println("Renter" + (i+1) + ":");
			//TODO could make more robust name checking here .
			System.out.println("Please enter your name: ");
			name = scanner.nextLine();
			renters[i] = new Renter(name, numRenters);
			
			int totalBid = 0;
			// each renter's sum of bids will need to equal the total rent
			while (totalBid != totalRent) {
				totalBid = 0;
				System.out.println("The sum of your bids must = the total rent!");
				// enter the initial bids for each room
				for (int j = 0; j < numRenters; j++) {
					Integer valuation = -1;
					while (valuation < 0 || valuation >= totalRent) {
						System.out.println("Please enter your (integer) valuation for room " + (j+1) + ": Must be > 0 and < total rent");
				
						
						while (!scanner.hasNextInt()){
							System.out.println("That's not an integer! Please enter a valid bid!");
							scanner.next();
						}
						
						valuation = scanner.nextInt();
						scanner.nextLine();
						
					}
					renters[i].setValuation(j, valuation);
					totalBid += valuation;
												
					}
				}
			}
		}
	
	
	
}
