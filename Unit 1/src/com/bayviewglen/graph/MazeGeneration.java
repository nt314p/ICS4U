package com.bayviewglen.graph;

import java.util.ArrayList;

import com.bayviewglen.graph.Graph.Edge;

import processing.core.PApplet;

public class MazeGeneration extends PApplet {

	static Graph g;
	static Graph mst;
	static int[][] grid;
	static int size = 25;
	static int spacing = 36;
	static int offset = 40;
	static float strokeW = (float) (spacing * 2.0 / 3);
	static boolean gf = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		g = new Graph((int) Math.pow(size, 2));

		// horizontal range|adj rng
		for (int x = 0; x < size - 1; x++) { // 0 - 8 (0 - 8)
			for (int y = 0; y < size; y++) { // 0 - 9 (0 - 90)
				g.addEdge(x + y * size, x + 1 + y * size, (int) (20000 * Math.random()));
			}
		}

		// vertical
		for (int x = 0; x < size; x++) { // 0 - 9 (0 - 9)
			for (int y = 0; y < size - 1; y++) { // 0 - 8 (0 - 80)
				g.addEdge(x + y * size, x + y * size + size, (int) (20000 * Math.random()));
			}
		}

		System.out.println("GE = " + g.E());
		mst = PrimsAlgorithm.minSpanningTree(g);
		System.out.println("MSTE = " + mst.E());
		PApplet.main("com.bayviewglen.graph.MazeGeneration");
	}

	public void settings() {
		size(1000, 1000);
	}

	public void setup() {
		fill(120, 50, 240);
		stroke(255);
		strokeWeight(spacing * 2 / 3);
		strokeCap(PROJECT);
		textSize(10);
		frameRate(10);
	}

	public void draw() {
		background(128);
		float a = offset - strokeW;
		float b = spacing * (size) + strokeW / 2;
		fill(0);
		strokeWeight(0);
		rect(a, a, b, b);
		strokeWeight(strokeW);
		if (gf) {
			drawGraph(g);
		} else {
			drawGraph(mst);
		}
	}

	public void keyPressed() {
		// gf = !gf;
	}

	public void drawGraph(Graph g) {
		for (int i = 0; i < g.V(); i++) {
			ArrayList<Edge> edges = g.adj(i);
			for (int j = 0; j < edges.size(); j++) {
				Edge e = edges.get(j);
				int x1 = e.src() % size * spacing + offset;
				int y1 = e.src() / size * spacing + offset;
				int x2 = e.dest() % size * spacing + offset;
				int y2 = e.dest() / size * spacing + offset;

				line(x1, y1, x2, y2);
				// text(i, x1 + 5, y1 - 5);
			}
		}
	}

}
