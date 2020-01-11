package neighbors;

public class Neighbors {
	static int[] donations;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//
		donations = new int[] { 1, 2, 3, 4, 5, 6, 6, 5, 4, 3, 2, 1 };
		System.out.println(solve());
	}

	public static int solve() {
		int max = -1;
		for (int i = 0; i < donations.length; ++i) {
			max = Math.max(max, solveReflected());
			rotateRight(donations);
		}
		return max;
	}

	public static int solveReflected() {
		int[] donB = new int[donations.length];
		for (int i = 0; i < donations.length; ++i) {
			donB[i] = donations[donations.length - 1 - i]; // reverse array
		}

		int[] collF = solveList(donations); // forward
		int[] collB = solveList(donB); // backward
		int[] collBR = new int[donations.length]; // backward array reversed again
		for (int i = 0; i < collBR.length; ++i) { // so collF and collBR can be compared
			collBR[i] = collB[collB.length - 1 - i];
		}

		int[] fTObr = insertList(collF, collBR); // front -> back reversed
		int[] brTOf = insertList(collBR, collF); // back reversed -> front

		int fTObrSum = worth(fTObr, donations);
		int brTOfSum = worth(brTOf, donations);
		System.out.println("F->BR: " + fTObrSum);
		System.out.println("BR->F: " + brTOfSum);
		return Math.max(fTObrSum, brTOfSum);
	}

	public static int[] insertList(int[] from, int[] to) {
		int[] ret = new int[to.length];
		for (int i = 0; i < ret.length; ++i) { // copy to -> ret
			ret[i] = to[i];
		}

		for (int i = 0; i < donations.length; ++i) {
			if (from[i] == 1) { // checking if there is a value to insert
				if (ret[posIndex(i - 1)] == 0 && ret[posIndex(i + 1)] == 0) {
					// if it can be inserted (left and right are both 0), insert
					ret[i] = 1;
				}
			}
		}
		return ret;
	}

	public static int[] solveList(int[] don) {
		if (donations.length == 1) // quick fix
			return new int[] { 1 };
		int[] collect = new int[donations.length];
		for (int i = 0; i < collect.length; ++i) // -1: undecided, 0: no collection, 1: collect
			collect[i] = -1; // initialize array with undecided
		for (int i = 0; i < collect.length; ++i) { // iterate through donations
			boolean isWorth = (value(i, collect, don) >= value(i - 1, collect, don) + value(i + 1, collect, don));
			if (isWorth) { // is the current index worth more than its neighbors
				collect[posIndex(i - 1)] = 0; // front and back are not to be collected
				collect[posIndex(i + 1)] = 0;
			}
			collect[posIndex(i)] = isWorth ? 1 : 0; // isWorth decides whether donation is collected
		}
		return collect;
	}

	public static void rotateRight(int[] arr) {
		int last = arr[arr.length - 1];
		for (int i = arr.length - 1; i > 0; --i) {
			arr[i] = arr[i - 1];
		}
		arr[0] = last;
	}

	public static int worth(int[] coll, int[] don) {
		int ret = 0;
		for (int i = 0; i < don.length; ++i) {
			ret += coll[i] * don[i]; // coll should only consist of 0 and 1
		}
		return ret;
	}

	public static int posIndex(int index) { // reassign index to positive value
		return Math.floorMod(index, donations.length);
	}

	public static int value(int i, int[] coll, int[] don) { // return 0 if no donations being collected
		return Math.abs(coll[posIndex(i)] * don[posIndex(i)]);
	}

}
