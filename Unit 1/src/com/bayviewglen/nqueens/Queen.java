package com.bayviewglen.nqueens;

public class Queen {

	private int x;
	private int y;

	public Queen(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void incX() {
		x++;
	}

	public void incY() {
		y++;
	}

	public boolean canShift(int n) {
		return x + 1 < n;
	}

	public boolean conflictsWith(Queen other) {
		boolean a = x == other.x || y == other.y;
		boolean b = a || (x != other.x && Math.abs((y - other.y) / ((double) (x - other.x))) == 1);
		return b;
	}

	public static boolean hasConflict(Queen[] queens) {
		boolean conflicts = false;

		for (int i = 0; i < queens.length - 1; i++) {
			for (int j = i + 1; j < queens.length; j++) {
				conflicts |= queens[i].conflictsWith(queens[j]);
				if (conflicts) {
					return true;
				}
			}
		}
		return conflicts;
	}

	public String print(int n) {
		String ret = "";
		for (int i = 0; i < x; i++) {
			ret += " +";
		}
		ret += " Q";
		for (int i = x + 1; i < n; i++) {
			ret += " +";
		}
		return ret;
	}
	
	/*
	 Q + + + + + + + + + + + + + + + + + + + + + + + + + + +
	 + + Q + + + + + + + + + + + + + + + + + + + + + + + + +
	 + + + + Q + + + + + + + + + + + + + + + + + + + + + + +
	 + Q + + + + + + + + + + + + + + + + + + + + + + + + + +
	 + + + Q + + + + + + + + + + + + + + + + + + + + + + + +
	 + + + + + + + + Q + + + + + + + + + + + + + + + + + + +
	 + + + + + + + + + + Q + + + + + + + + + + + + + + + + +
	 + + + + + + + + + + + + Q + + + + + + + + + + + + + + +
	 + + + + + + + + + + + + + + Q + + + + + + + + + + + + +
	 + + + + + + + + + + + + + + + + Q + + + + + + + + + + +
	 + + + + + + + + + + + + + + + + + + + + + + Q + + + + +
	 + + + + + + + + + + + + + + + + + + + + + + + + Q + + +
	 + + + + + + + + + + + + + + + + + + + + + Q + + + + + +
	 + + + + + + + + + + + + + + + + + + + + + + + + + + + Q
	 + + + + + + + + + + + + + + + + + + + + + + + + + Q + +
	 + + + + + + + + + + + + + + + + + + + + + + + Q + + + +
	 + + + + + + + + + + + + + + + + + + + + + + + + + + Q +
	 + + + + + + Q + + + + + + + + + + + + + + + + + + + + +
	 + + + + + + + + + + + Q + + + + + + + + + + + + + + + +
	 + + + + + + + + + + + + + + + Q + + + + + + + + + + + +
	 + + + + + + + + + + + + + + + + + Q + + + + + + + + + +
	 + + + + + + + Q + + + + + + + + + + + + + + + + + + + +
	 + + + + + + + + + Q + + + + + + + + + + + + + + + + + +
	 + + + + + + + + + + + + + Q + + + + + + + + + + + + + +
	 + + + + + + + + + + + + + + + + + + + Q + + + + + + + +
	 + + + + + Q + + + + + + + + + + + + + + + + + + + + + +
	 + + + + + + + + + + + + + + + + + + + + Q + + + + + + +
	 + + + + + + + + + + + + + + + + + + Q + + + + + + + + +
	 */

}
