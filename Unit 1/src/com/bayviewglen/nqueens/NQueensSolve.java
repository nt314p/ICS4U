package com.bayviewglen.nqueens;


public class NQueensSolve {
	
	public static void main(String[] args) {
		int n = 29;
		QueenStack solution = solve(n);
		
		while (!solution.isEmpty()) {
			System.out.println(solution.pop().print(n));
		}
	}
	
	public static QueenStack solve(int n) {

		QueenStack queens = new QueenStack();
		QueenStack tempConflict = new QueenStack();
		
		int filled = 0;

		queens.push(new Queen(0, 0));

		while (filled != n) {

			boolean conflict = false;

			Queen top = queens.pop();
			tempConflict.push(top);
			while (!queens.isEmpty() && !conflict) { // pop off
				tempConflict.push(queens.pop());
				if (tempConflict.peek().conflictsWith(top)) {
					conflict = true;
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
		
		
		if (queens.isEmpty()) {
			System.out.println("No solutions.");
		} else {
			queens.pop();
		}
		
		return queens;
	}
}
