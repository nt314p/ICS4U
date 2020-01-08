package neighbors;

public class Neighbors {
	static int[] donations;
	static int[] collected;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		donations = new int[] {4, 8, 5, 8, 4, 3};
		System.out.println(solve());
	}

	public static int solve() {
		int maxDonations = 0;
		if (donations.length == 1) // quick fix
			return donations[0];
		collected = new int[donations.length];
		for (int i = 0; i < collected.length; ++i) // -1: undecided, 0: no collection, 1: collect
			collected[i] = -1; // initialize array with undecided
		for (int i = 0; i < collected.length; ++i) { // iterate through donations
			boolean isWorth = (value(i) >= value(i - 1) + value(i + 1));
			if (isWorth) { // is the current index worth more than its neighbors
				collected[posIndex(i - 1)] = 0; // front and back are not to be collected
				collected[posIndex(i + 1)] = 0;
			}
			collected[posIndex(i)] = isWorth ? 1 : 0; // isWorth decides whether donation is collected
			maxDonations += donations[i] * collected[i]; // collected donation
		}
		return maxDonations;
	}

	public static int posIndex(int index) { // reassign index to positive value
		return Math.floorMod(index, collected.length);
	}

	public static int value(int i) { // return 0 if no donations being collected
		return Math.abs(collected[posIndex(i)] * donations[posIndex(i)]);
	}

}
