package com.bayviewglen.graph;

import java.util.ArrayList;

public class Graph {

	private ArrayList<Edge>[] data;

	public Graph(int vertices) {
		data = new ArrayList[vertices];
		for (int i = 0; i < data.length; i++) {
			data[i] = new ArrayList<Edge>();
		}
	}

	public void addEdge(int src, int dest, int weight) {
		if (src != dest) {
			data[src].add(new Edge(src, dest, weight));
			data[dest].add(new Edge(dest, src, weight));
		}
	}
	
	public void addEdge(Edge e) {
		addEdge(e.src, e.dest, e.weight);
	}

	public int V() {
		return data.length;
	}

	public int E() {
		int e = 0;
		for (ArrayList<Edge> edges : data) {
			e += edges.size();
		}
		return e;
	}

	public ArrayList<Edge> adj(int v) {
		return data[v];
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			for (Edge e : data[i]) {
				if (e.dest >= i) // remove for full edge print
					ret += "E: " + i + "-" + e.dest + " W: " + e.weight + " | ";
			}
			ret += "\n";
		}
		return ret;
	}

	static class Edge {
		private int src;
		private int dest;
		private int weight;

		public Edge(int src, int dest, int weight) {
			this.src = src;
			this.dest = dest;
			this.weight = weight;
		}
		
		public int src() {
			return src;
		}

		public int dest() {
			return dest;
		}

		public int weight() {
			return weight;
		}
		
		public String toString() {
			return "S: " + src + "D: " + dest + " W: " + weight;
		}
	}

	public static void depthFirstSearch(Graph g, int v) {
		boolean[] marked = new boolean[g.data.length];
		int[] edgeTo = new int[g.data.length];

		dfs(g, v, marked, edgeTo);
		for (int i = 0; i < marked.length; i++) {
			if (marked[i]) {
				System.out.print(i + " ");
			}
		}
		System.out.println();

		for (int i = 0; i < edgeTo.length; i++) {
			if (marked[i]) {
				System.out.println(i + " to " + edgeTo[i]);
			} else {
				System.out.println(i + " not connected");
			}
		}
	}

	private static void dfs(Graph g, int v, boolean[] marked, int[] edgeTo) {
		marked[v] = true;
		ArrayList<Edge> neighbors = g.data[v];
		for (Edge n : neighbors) {
			if (!marked[n.dest]) {
				dfs(g, n.dest, marked, edgeTo);
				edgeTo[n.dest] = v;
			}
		}
	}
}
