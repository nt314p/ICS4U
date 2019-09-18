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

	public boolean canShift(int n) {
		return x + 1 < n;
	}

	public boolean conflictsWith(Queen other) {
		return x == other.x || y == other.y || Math.abs((y - other.y) / ((double) (x - other.x))) == 1;
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

}
