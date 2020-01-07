package neighbors;

public class Neighbors {
	static int[] donations;
	static int[] collected;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		donations = new int[]{};
		System.out.println(solve());
	}

	public static int solve() {
		if (donations.length == 1) 
			return donations[0];
		collected = new int[donations.length];
		for (int i = 0; i < collected.length; ++i)
			collected[i] = -1; // initialize
		
		for (int i = 0; i < collected.length; ++i) {
			boolean isWorth = (value(i) >= value(i - 1) + value(i + 1));
			if (isWorth) { // is the current index worth more than its neighbors
				collected[posIndex(i - 1)] = 0; // front and back are out
				collected[posIndex(i + 1)] = 0;
			}
			collected[posIndex(i)] = isWorth ? 1 : 0;
		}
		int maxDonations = 0;
		for (int i = 0; i < collected.length; ++i) {
			maxDonations += donations[i] * collected[i];
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
