import java.util.Arrays;
import java.util.Scanner;

/**
 * This is a simple program to demonstrate a rent division algorithm. See README
 * for details
 * 
 * @author hartneray (theory co-developed with Josh Levin)
 * 
 */
public class RentAlgo {

	// numRenters = numRooms
	private static final int numRenters = 3;

	// indexed by room. e.g., roomOwners[0] contains index of player who owns
	// room 0
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

	public static void main(String[] args) {

		// set each room to have no owner
		Arrays.fill(roomOwners, -1);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter total rent: ");
		totalRent = Integer.parseInt(scanner.nextLine());

		String name = "";

		// error check for string
		System.out.println("Please enter epsilon (measure of envy): ");
		epsilon = Integer.parseInt(scanner.nextLine());

		for (int i = 0; i < numRenters; i++) {
			System.out.println("Renter" + i + ":");
			System.out.println("Please enter your name: ");
			name = scanner.nextLine();
			renters[i] = new Renter(name, numRenters);

			int totalBid = 0;
			while (totalBid != totalRent) {
				totalBid = 0;
				for (int j = 0; j < numRenters; j++) {
					Integer valuation = -1;
					while (valuation < 0 || valuation > totalRent) {
						System.out
								.println("Please enter your (integer, nonegative) valuation for room "
										+ j + ": ");
						valuation = Integer.parseInt(scanner.nextLine());
						renters[i].setValuation(j, valuation);
						totalBid += valuation;
					}
				}

			}

		}

		scanner.close();

		boolean playerWithoutRoom = true;

		// algorithm stops once everybody has a room
		while (playerWithoutRoom) {

			// iterate over the rooms
			for (int i = 0; i < numRenters; i++) {

				// put a room up for auction if it hasn't been claimed
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

		// print out how much each person ultimately bid for their room
		int totalPaid = calculateTotalPayment();
		// redistribute any extra cash evenly
		redistribute(totalPaid);

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
					+ (renters[roomOwners[i]].getValuation(i) + epsilon)
					+ " for room " + i);
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
		System.out.println("Paid difference = " + (totalPaid- totalRent));
		int cashBack = (totalPaid - totalRent) / 3;
		System.out.println("Cashback = " + cashBack);
		for (int i = 0; i < numRenters; i++) {
			int payment = renters[roomOwners[i]].getValuation(i) + epsilon
					- cashBack;
			System.out.println("Renter " + roomOwners[i] + " ultimately pays "
					+ payment + " for room " + i);
		}
	}
}
