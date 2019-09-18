package com.bayviewglen.nqueens;

public class NQueensSolve {

	public static void main(String[] args) {

		for (int p = 1; p <= 20; p++) {

			QueenStack queens = new QueenStack();
			QueenStack tempConflict = new QueenStack();

			int n = p;
			int filled = 0;

			queens.push(new Queen(0, 0));

			while (filled != n) {

				boolean conflict = false;

				Queen top = queens.pop();
				tempConflict.push(top);
				while (!queens.isEmpty()) { // pop off
					tempConflict.push(queens.pop());
					if (tempConflict.peek().conflictsWith(top)) {
						conflict = true;
						break;
					}
				}
				while (!tempConflict.isEmpty()) { // pop on
					queens.push(tempConflict.pop());
				}

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
						break;
					}
					queens.peek().incX();
				}
			}
			System.out.println("n = " + n);
			if (!queens.isEmpty()) {
				queens.pop();
			} else {
				System.out.println("NO SOLUTIONS");
			}

			while (!queens.isEmpty()) {
				System.out.println(queens.pop().print(n));
			}
		}
	}

}
