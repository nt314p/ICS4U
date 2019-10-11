package com.bayviewglen.graph;

import java.util.Scanner;

public class TurdHackOne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);

		String solve = "";
		int vh = -1;
		int dy = -1;
		int dx = -1;
		double g = 4.9;

		while (true) {
			System.out.println();
			System.out.println("vh");
			if (in.hasNextInt()) {
				vh = in.nextInt();
			} else {
				in.next();
				solve = "vh";
			}

			System.out.println("dy");
			if (in.hasNextInt()) {
				dy = in.nextInt();
			} else {
				in.next();
				solve = "dy";
			}

			System.out.println("dx");
			if (in.hasNextInt()) {
				dx = in.nextInt();
			} else {
				in.next();
				solve = "dx";
			}

			if (solve.equals("dx")) {
				System.out.println(Math.pow(dy / g, 0.5) * vh);
			} else if (solve.equals("vh")) {
				System.out.println(dx / Math.pow(dy / g, 0.5));
			} else if (solve.equals("dy")) {
				System.out.println(Math.pow(dx * 1.0 / vh, 2) * g);
			}
		}
	}

}
