package zigzag;

public class ZigZag {
	static int[][] intervals;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 2, 1, 0, 1, 0 }; 
		System.out.println(solveSmart(arr));
		System.out.println(solveBrute(arr));
	}
	
	public static int solveSmart(int[] arr) {
		if (arr.length < 2)
			return arr.length;
		int flips = 2;
		int d = (int) Math.signum(arr[1] - arr[0]);
		for (int i = 1; i < arr.length - 1; i++) {
			if (((int) Math.signum(arr[i + 1] - arr[i])) == -d) {
				flips++;
				d *= -1;
			}
		}
		return flips;
	}

	public static String solveBrute(int[] arr) {
		int longest = 0;
		int[] interval = { -1, -1 };
		for (int i = 0; i < arr.length - 1; ++i) {
			for (int j = i + 1; j < arr.length; ++j) {
				int[] ret = new int[j - i];
				for (int k = 0; k < j - i; ++k) {
					ret[k] = arr[k + i];
				}
				if (isZigzag(ret) && ret.length > longest) {
					longest = ret.length;
					interval[0] = i;
					interval[1] = j;
				}
			}
		}
		return interval[0] + ", " + interval[1] + " is the interval!";
	}

	public static boolean isZigzag(int[] arr) {
		if (arr.length < 2) {
			return true;
		}
		int d = (int) Math.signum(arr[1] - arr[0]);
		for (int i = 1; i < arr.length - 1; i++) {
			if (((int) Math.signum(arr[i + 1] - arr[i])) != -d) {
				return false;
			}
			d *= -1;
		}
		return true;
	}

}
