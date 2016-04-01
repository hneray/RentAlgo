import java.util.*;

/**
 * Basic renter information for the RentAlgo This class describes the
 * information pertaining to a renter and his/her valuations on the rooms.
 * 
 * @author hartneray
 * 
 */
public class Renter {
	private String name;

	// the renter's valuations of the rooms, indexed by room number
	// e.g., valuations[0] = this player's valuations of room 0
	private int[] valuations;

	public Renter(String name, int numValuations) {
		this.name = name;
		this.valuations = new int[numValuations];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getValuations() {
		return Arrays.copyOf(valuations, valuations.length);
	}

	public int getValuation(int roomNumber) {
		return valuations[roomNumber];
	}

	// for ease of access
	public void setValuation(int roomNumber, int valuation) {
		if (valuation < 0) {
			System.out.println("Bid must be > 0");
		}
		this.valuations[roomNumber] = valuation;
	}

	/**
	 * Decrement each of the renter's valuations by epsilon every time he/she
	 * claims a room. This means the renter will only swap in his/her room if
	 * he/she sees a deal that's epsilon better than his/her current deal.
	 */
	public void decrementValuations(int epsilon) {
		for (int i = 0; i < valuations.length; i++) {
			valuations[i] -= epsilon;
		}
	}

}
