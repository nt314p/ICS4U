package neighbors;

public class Neighbors {
	static int[] donations;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//
		donations = new int[] { 8, 7, 6, 5, 4, 3, 2, 1, 9 };
		System.out.println(solve());
	}

	public static int solve() {

	}

	public static int[] solveList() {
		if (donations.length == 1) // quick fix
			return new int[] { 1 };
		int[] collect = new int[donations.length];
		for (int i = 0; i < collect.length; ++i) // -1: undecided, 0: no collection, 1: collect
			collect[i] = -1; // initialize array with undecided
		for (int i = 0; i < collect.length; ++i) { // iterate through donations
			boolean isWorth = (value(i, collect) >= value(i - 1, collect) + value(i + 1, collect));
			if (isWorth) { // is the current index worth more than its neighbors
				collect[posIndex(i - 1)] = 0; // front and back are not to be collected
				collect[posIndex(i + 1)] = 0;
			}
			collect[posIndex(i)] = isWorth ? 1 : 0; // isWorth decides whether donation is collected
		}
		return collect;
	}

	public static int posIndex(int index) { // reassign index to positive value
		return Math.floorMod(index, donations.length);
	}

	public static int value(int i, int[] collected) { // return 0 if no donations being collected
		return Math.abs(collected[posIndex(i)] * donations[posIndex(i)]);
	}

}
