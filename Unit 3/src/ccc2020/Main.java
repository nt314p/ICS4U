import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);
		int[] inTemp = new int[10001];
		int len = reader.nextInt();
		int[][] observations = new int[len][2];

		for (int i = 0; i < inTemp.length; i++) {
			inTemp[i] = 123456789;
		}

		for (int i = 0; i < len; i++) {
			int in = reader.nextInt();
			inTemp[in] = reader.nextInt();
		}
		int index = 0;
		for (int i = 0; i < inTemp.length; i++) {
			if (inTemp[i] != 123456789) {
				observations[index++] = new int[] { i, inTemp[i] };
			}
		}

		/*
		 * for (int i = 0; i < len; i++) { int in = reader.nextInt(); int j; for (j = 0;
		 * j < observations.size(); j++) { if (observations.get(j)[0] > in) { break; } }
		 * observations.add(j, new int[]{in, reader.nextInt()}); }
		 */

		double maxSpeed = Double.MIN_VALUE;
		for (int i = 0; i < len - 1; i++) {
			double dD = (observations[i + 1][1] - observations[i][1]);
			double dT = (observations[i + 1][0] - observations[i][0]);
			double speed = Math.abs(dD / dT);
			if (maxSpeed < speed) {
				maxSpeed = speed;
			}
		}
		System.out.println(maxSpeed);

		reader.close();

	}

}
