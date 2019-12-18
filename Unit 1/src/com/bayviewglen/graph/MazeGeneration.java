package com.bayviewglen.graph;

import java.util.ArrayList;

import com.bayviewglen.graph.Graph.Edge;

import processing.core.PApplet;

public class MazeGeneration extends PApplet {

	static Graph g;
	static Graph mst;
	static int[][] grid;
	static int spacing = 12;
	static int len = 800;
	static int size = len / spacing - 2;
	static int offset = 20;
	static float strokeW = (float) (spacing * 2.0 / 3);
	static boolean gf = false;
	static boolean step = false;
	static int range = 100;
	static boolean t = false;
	static boolean u = false;
	static boolean part = true;

	static int mstV = 1; // how many vertices have been added
	static ArrayList<Edge> unselected = new ArrayList<Edge>(); // list of unselected edges
	static boolean[] marked; // vertices that have been marked
	static int currVertex = 0; // start at vertex 0

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		reset();

//		System.out.println("GE = " + g.E());
//		// mst = PrimsAlgorithm.minSpanningTree(g);
//		System.out.println("MSTE = " + mst.E());
		PApplet.main("com.bayviewglen.graph.MazeGeneration");
	}

	public void settings() {
		size(len, len);
	}

	public void setup() {
		fill(120, 50, 240);
		stroke(255);
		strokeWeight(spacing * 2 / 3);
		strokeCap(PROJECT);
		textSize(12);
		frameRate(6000);
	}

	public void draw() {
		background(128);
		float a = offset - strokeW;
		float b = spacing * (size) + strokeW / 2;
		fill(0);
		strokeWeight(0);
		rect(a, a, b, b);
		strokeWeight(strokeW);
		drawGraph(mst);
		if (gf) {
			stepMST(g);
		}
	}

	public void keyPressed() {
		if (mstV == mst.V() && key == ' ') {
			reset();
		} else {
			switch (key) {
			case ' ':
				gf = !gf;
				break;
			case 't':
				t = !t;
				break;
			case 'u':
				u = !u;
				break;
			case 's':
				stepMST(g);
				break;
			}
		}
	}

	public void drawGraph(Graph g) {
		if (u) {
			stroke(255, 0, 0);
			for (int j = 0; j < unselected.size(); j++) {
				Edge e = unselected.get(j);
				int x1 = e.src() % size * spacing + offset;
				int y1 = e.src() / size * spacing + offset;
				int x2 = e.dest() % size * spacing + offset;
				int y2 = e.dest() / size * spacing + offset;

				line(x1, y1, x2, y2);
			}
			stroke(255);
		}

		for (int i = 0; i < g.V(); i++) {
			ArrayList<Edge> edges = g.adj(i);
			for (int j = 0; j < edges.size(); j++) {
				Edge e = edges.get(j);
				int x1 = e.src() % size * spacing + offset;
				int y1 = e.src() / size * spacing + offset;
				int x2 = e.dest() % size * spacing + offset;
				int y2 = e.dest() / size * spacing + offset;
				line(x1, y1, x2, y2);
			}
		}
		if (t) {
			for (int i = 0; i < g.V(); i++) {
				ArrayList<Edge> edges = g.adj(i);
				for (int j = 0; j < edges.size(); j++) {
					Edge e = edges.get(j);
					int x1 = e.src() % size * spacing + offset;
					int y1 = e.src() / size * spacing + offset;
					int x2 = e.dest() % size * spacing + offset;
					int y2 = e.dest() / size * spacing + offset;
					text(e.weight(), (x1 + x2) / 2 - 5, (y1 + y2) / 2 + 5);
				}
			}
			if (u) {
				for (int j = 0; j < unselected.size(); j++) {
					Edge e = unselected.get(j);
					int x1 = e.src() % size * spacing + offset;
					int y1 = e.src() / size * spacing + offset;
					int x2 = e.dest() % size * spacing + offset;
					int y2 = e.dest() / size * spacing + offset;
					text(e.weight(), (x1 + x2) / 2 - 5, (y1 + y2) / 2 + 5);
				}
			}
		}
	}

	public static void reset() {
		mstV = 1;
		unselected = new ArrayList<Edge>();
		currVertex = 0;

		g = genGridGraph();
		marked = new boolean[g.V()];
		mst = new Graph(g.V());
	}

	public void stepMST(Graph g) {

		if (mstV != mst.V()) {
			if (part) {
				marked[currVertex] = true; // current vertex has edge to it so mark
				for (Edge e : g.adj(currVertex)) {
					if (!marked[e.dest()]) // adding edge only if it doesn't connect
						unselected.add(e); // to a vertex that has already been used
				}
				part = false;
			} else {

				Edge cheapest = unselected.get(0);
				for (int i = 1; i < unselected.size(); i++) { // find cheapest edge
					if (cheapest.weight() > unselected.get(i).weight())
						cheapest = unselected.get(i);
				}
				mst.addEdge(cheapest);
				mstV++;
				currVertex = cheapest.dest();
				for (int i = 0; i < unselected.size(); i++) {
					if (unselected.get(i).dest() == currVertex) {
						unselected.remove(i);
						i--; // remove add edges with dest because it has been marked
					}
				}
				part = true;
			}
		}
	}

	public static Graph genGridGraph() {
		Graph g = new Graph((int) Math.pow(size, 2));

		// horizontal range|adj rng
		for (int x = 0; x < size - 1; x++) { // 0 - 8 (0 - 8)
			for (int y = 0; y < size; y++) { // 0 - 9 (0 - 90)
				g.addEdge(x + y * size, x + 1 + y * size, (int) (range * Math.random()));
			}
		}

		// vertical
		for (int x = 0; x < size; x++) { // 0 - 9 (0 - 9)
			for (int y = 0; y < size - 1; y++) { // 0 - 8 (0 - 80)
				g.addEdge(x + y * size, x + y * size + size, (int) (range * Math.random()));
			}
		}
		return g;
	}

}
