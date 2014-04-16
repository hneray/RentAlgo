/**
 * Basic renter information for the RentAlgo
 * 
 * @author hartneray
 * 
 */
public class Renter {
	String name;

	// the renter's valuations of the rooms, indexed by room number
	// e.g., valuations[0] = this player's valuations of room 0
	int[] valuations;

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

	// could return the reference instead of a copy if concerned about
	// performance
	public int[] getValuations() {
		return valuations.clone();
	}

	public int getValuation(int roomNumber) {
		return valuations[roomNumber];
	}

	// for ease of access
	public void setValuation(int roomNumber, int valuation) {
		this.valuations[roomNumber] = valuation;
	}

	public void decrementValuations(int epsilon) {
		for (int i = 0; i < valuations.length; i++) {
			valuations[i] -= epsilon;
		}
	}

}
