package flowers;

public class Flowers {
	static int[] height = { 1, 2, 3 };
	static int[] bloom = { 1, 0, 1 };
	static int[] wilt = { 2, 2, 1 };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] order = new int[height.length];
		for (int i = 0; i < order.length; ++i) {
			order[i] = i;
		}
		System.out.println(heightAtDay(0, 0));
		System.out.println(heightAtDay(0, 1));
		System.out.println(heightAtDay(0, 2));
		System.out.println("start");

		for (int flower : order) {
			System.out.println(flower);
		}
		int[] solution = solveDyn(0, order);
		for (int flower : solution) {
			System.out.println(flower);
		}
	}

	public static int[] solveDyn(int day, int[] order) {

		for (int i = 1; i < order.length; ++i) { // perform insertion sort on flowers
			int j = i - 1; // on one single day (sort so no blocking)
			int current = order[i];
			while (j >= 0 && (heightAtDay(day, j) > heightAtDay(day, i))) {
				order[j + 1] = order[j];
				j--;
			}
			order[j + 1] = current;
		}

		int numNonBlooms = 0;
		for (int flower : order) { // count number of non bloomers
			if (heightAtDay(day, flower) == 0)
				numNonBlooms++;
		}
		if (numNonBlooms == 0)
			return order; // if there are no non bloomers, then nothing can be done

		int[] nextDay = new int[numNonBlooms];
		int[] blooms = new int[order.length - numNonBlooms]; // sorted
		for (int i = 0; i < order.length; ++i) {
			if (i < numNonBlooms)
				nextDay[i] = order[i]; // only transfer non bloomers to the next day
			else
				blooms[i - numNonBlooms] = order[i];
		} // since only they can move around
		int[] solved = solveDyn(day + 1, nextDay); // INSERT AROUND SORTED LIST OR MAYBE EVEN INSIDE
		
		int[] updateOrder = new int[order.length];
		int m = 0;
		int n = 0;
		for (int i = 0; i < updateOrder.length; ++i) { // reinsert sorted flowers into list
			if (n == solved.length || (m != blooms.length && heightAtDay(day + 1, blooms[m]) < heightAtDay(day + 1, solved[n]))) {
				updateOrder[i] = blooms[m++];
			} else {
				updateOrder[i] = solved[n++];
			}
		}
		return updateOrder; // return sorted flowers

	}

	public static int heightAtDay(int day, int flower) {
		return ((day >= bloom[flower] && day <= wilt[flower]) ? height[flower] : 0);
	}

}
