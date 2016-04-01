/**
 * This algorithm divides rent between 3 people for 3 rooms. See README for
 * details.
 * 
 * @author hartneray (theory co-developed with Josh Levin)
 * 
 */
public class RentAlgo {

	// numRenters will = numRooms
	private static final int numRenters = 3;

	// current owners of the rooms, indexed by room.
	// e.g., roomOwners[0] contains the index of the player who owns room 0
	private int[] roomOwners = new int[numRenters];

	// array of the renters
	private Renter[] renters = new Renter[numRenters];

	// maximum "envy" value
	private int epsilon;

	// total rent required for the entire apartment
	private int totalRent;

	public RentAlgo(int[] roomOwners, Renter[] renters, int epsilon, int totalRent) {
		this.roomOwners = roomOwners;
		this.renters = renters;
		this.epsilon = epsilon;
		this.totalRent = totalRent;
	}

	/**
	 * This is the main logic of the rent division algorithm. Assigns rooms to
	 * each renter based on their initial bids with the ultimate result of
	 * having no renter see another renter get a deal that is epsilon better
	 * than his/hers.
	 */
	public void runAuction() {

		// algorithm stops once everybody has a room
		boolean playerWithoutRoom = true;

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

					// and assign the room the player just won
					roomOwners[i] = highestBidder;

					// now update the room valuations for the player who "won"
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
	 * Release room owned by this player back to the market
	 */
	public boolean releaseRoomOwnedByPlayer(int playerNum) {
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
	 * Check if there is at least one open room.
	 */
	public boolean openRoom() {
		for (int i = 0; i < numRenters; i++) {
			if (roomOwners[i] == -1) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Calculate the total amount collected. We have to add back epsilon to the
	 * player's bids because the algorithm decrements their bids by epsilon
	 * whenever they have the highest bid, including on the room they ultimately
	 * won
	 * 
	 * @return the total payment
	 */
	public int calculateTotalPayment() {
		int totalPaid = 0;
		for (int i = 0; i < numRenters; i++) {
			System.out.println("Renter " + renters[roomOwners[i]].getName() + " paid "
					+ (renters[roomOwners[i]].getValuation(i) + epsilon) + " for room " + (i + 1));
			totalPaid += (renters[roomOwners[i]].getValuation(i) + epsilon);
		}
		return totalPaid;
	}

	/**
	 * Redistribute evenly any difference between what the players bid and the
	 * total rent required
	 */
	public void redistribute(int totalPaid) {
		// we'll round down here
		System.out.println("Total paid = " + totalPaid);
		System.out.println("Total Rent = " + totalRent);
		System.out.println("Paid difference = " + (totalPaid - totalRent));
		int cashBack = (totalPaid - totalRent) / 3;
		System.out.println("Cashback = " + cashBack);

		for (int i = 0; i < numRenters; i++) {
			int payment = renters[roomOwners[i]].getValuation(i) + epsilon - cashBack;
			System.out.println("Renter " + renters[roomOwners[i]].getName() + " ultimately pays " + payment
					+ " for room " + (i + 1));
		}
	}
}
