import java.util.*;


/**
 * This is a simple program to demonstrate a rent division algorithm. See README
 * for details
 * 
 * @author hartneray (theory co-developed with Josh Levin)
 * 
 */
public class RentAlgo {

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

		//saveNamesAndBids(scanner);

		scanner.close();

		//runAuction();

		// print out how much each person ultimately bid for their room
		//int totalPaid = calculateTotalPayment();
		// redistribute any extra cash evenly
		//redistribute(totalPaid);

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
			totalRent = Integer.parseInt(scanner.nextLine());
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
			
			epsilon = (scanner.nextInt());
		}

	}

	public static void saveNamesAndBids(Scanner scanner) {
		String name = ""; // could make this stringbuilder?

		// ask each renter for his/her name and his/her initial bids
		for (int i = 0; i < numRenters; i++) {
			System.out.println("Renter" + (i+1) + ":");
			//TODO
			while (!scanner.hasNext()){
				System.out.println("That's not an integer! Please enter a valid epsilon!");
				scanner.next();
			}
			System.out.println("Please enter your name: ");
			name = scanner.nextLine();
			renters[i] = new Renter(name, numRenters);

			int totalBid = 0;
			// each renter's sum of bids will need to equal the total rent
			while (totalBid != totalRent) {
				totalBid = 0;
				// enter the initial bids for each room
				for (int j = 0; j < numRenters; j++) {
					Integer valuation = -1;
					while (valuation < 0 || valuation > totalRent) {
						System.out.println("Please enter your (integer, nonegative) valuation for room " + (j+1) + ": ");
						valuation = Integer.parseInt(scanner.nextLine());
						renters[i].setValuation(j, valuation);
						totalBid += valuation;
					}
				}
			}
		}
	}

	public static void runAuction() {

		boolean playerWithoutRoom = true;

		// algorithm stops once everybody has a room
		while (playerWithoutRoom) {

			// iterate over the rooms
			for (int i = 0; i < numRenters; i++) {

				// put a room up for auction only if it hasn't been claimed
				if (roomOwners[i] == -1) {

					// find who's willing to pay the most for this room
					int highestBid = Integer.MIN_VALUE;
					int highestBidder = 0;
					for (int j = 0; j < numRenters; j++) {
						// ties are broken by choosing the renter with the lower
						// index
						if (renters[j].getValuation(i) > highestBid) {
							highestBid = renters[j].getValuation(i);
							highestBidder = j;
						}
					}

					// if the highest bidder previously owned a room, put it
					// back in the market
					releaseRoomOwnedByPlayer(highestBidder);

					// an assign the room the player just won
					roomOwners[i] = highestBidder;

					// now update the room valuations for the player who "won"
					// room i
					renters[roomOwners[i]].decrementValuations(epsilon);

					// check if everybody has a room——-if they do, we're done
					if (!openRoom()) {
						playerWithoutRoom = false;
						break;
					}

				}
			}

		}

	}

	/**
	 * Release rooms owned by this player back to the market
	 */
	public static boolean releaseRoomOwnedByPlayer(int playerNum) {
		for (int i = 0; i < numRenters; i++) {
			if (roomOwners[i] == playerNum) {
				roomOwners[i] = -1;
				// return since any player only owns one room at a time
				return true;
			}
		}
		return false;
	}

	/**
	 * check if there is at least one open room.
	 */
	public static boolean openRoom() {
		for (int i = 0; i < numRenters; i++) {
			if (roomOwners[i] == -1) {
				return true;
			}
		}
		return false;

	}

	// we have to add back epsilon
	// could use print format here
	/**
	 * Calculate the total amount collected. We have to add back epsilon to the
	 * player's bids because the algorithm decrements their bids by epsilon
	 * whenever they have the highest bid, including on the room they ultimately
	 * won
	 * 
	 * @return the total payment
	 */
	public static int calculateTotalPayment() {
		int totalPaid = 0;
		for (int i = 0; i < numRenters; i++) {
			System.out.println("Renter " + roomOwners[i] + " paid "
					+ (renters[roomOwners[i]].getValuation(i) + epsilon) + " for room " + i);
			totalPaid += (renters[roomOwners[i]].getValuation(i) + epsilon);
		}
		return totalPaid;
	}

	// we have to add back epsilon
	// could use print format here
	/**
	 * Redistribute evenly any difference between what the players bid and the
	 * total rent required
	 */
	public static void redistribute(int totalPaid) {
		// we'll round down here
		System.out.println("Total paid = " + totalPaid);
		System.out.println("Total Rent = " + totalRent);
		System.out.println("Paid difference = " + (totalPaid - totalRent));
		int cashBack = (totalPaid - totalRent) / 3;
		System.out.println("Cashback = " + cashBack);
		for (int i = 0; i < numRenters; i++) {
			int payment = renters[roomOwners[i]].getValuation(i) + epsilon - cashBack;
			System.out.println("Renter " + roomOwners[i] + " ultimately pays " + payment + " for room " + i);
		}
	}
}
