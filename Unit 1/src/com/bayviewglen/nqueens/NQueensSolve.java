package com.bayviewglen.nqueens;

import java.util.ArrayList;
import java.util.List;

public class NQueensSolve {

	public static void main(String[] args) {

		QueenStack queens = new QueenStack();

		int n = 20;
		int filled = 0;

		queens.push(new Queen(0, 0));

		while (filled != n) {

			boolean conflict = Queen.hasConflict(stackToArray(queens));

			if (!conflict) {
				filled++;
				queens.push(new Queen(0, filled));
			} else if (conflict && queens.peek().canShift(n)) {
				queens.peek().incX();
			} else if (conflict) {
				while (!queens.isEmpty() && !queens.peek().canShift(n)) {
					queens.pop();
					filled--;
				} // exits if stack is empty (no solution) or queen can be shifted
				if (queens.isEmpty()) {
					System.out.println("NO SOLUTIONS");
					break;
				}
				queens.peek().incX();
			}
		}
		if (!queens.isEmpty()) {
			queens.pop();
		}

		System.out.println("TERMINATED");
		Queen[] solve = stackToArray(queens);
		for (int i = 0; i < solve.length; i++) {
			System.out.println(solve[i].print(n));
		}
	}

	public static Queen[] stackToArray(QueenStack stack) {
		List<Queen> popped = new ArrayList<Queen>();
		while (!stack.isEmpty()) {
			popped.add(0, stack.pop());
		}
		Queen[] ret = new Queen[popped.size()];
		for (int i = 0; i < popped.size(); i++) {
			stack.push(popped.get(i));
			ret[i] = popped.get(i);
		}
		return ret;
	}

}
