package com.bayviewglen.nqueens;

import processing.core.PApplet;

public class NQueensVisualize extends PApplet {

	int n = 29;
	float width = 800 / (n + 1);
	QueenStack solution;
	int[][] colormap;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("com.bayviewglen.nqueens.NQueensVisualize");
	}

	public void settings() {
		size(800, 800);
	}

	public void setup() {
		solution = NQueensSolve.solve(n);
		colormap = new int[n][n];
		while (!solution.isEmpty()) {
			Queen current = solution.pop();
			colormap[current.getX()][current.getY()] = 2;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (current.getX() == i && current.getY() == j)
						continue;
					if (onDiagonal(new Queen(i, j), current)) {
						//if (colormap[i][j] != 2) {
							colormap[i][j] = 1;
						//}
					}
				}
			}
		}

	}

	public void draw() {
		background(128);
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (colormap[r][c] == 2) {
					fill(0);
					ellipse(r * width + width / 2, c * width + width / 2, width, width);
				} else if (colormap[r][c] == 1) {
					fill(0, 255, 0);
					rect(r * width, c * width, width, width);
				} else {
					fill(255);
					rect(r * width, c * width, width, width);
				}
			}
		}
	}

	private boolean onDiagonal(Queen q, Queen other) {
		if (q.getX() == other.getX()) {
			return false;
		}
		return Math.abs((q.getY() - other.getY()) / ((double) (q.getX() - other.getX()))) == 1;
	}

}
