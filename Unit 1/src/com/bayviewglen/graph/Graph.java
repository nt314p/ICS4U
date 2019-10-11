package com.bayviewglen.graph;

import java.util.ArrayList;

public class Graph {

	private ArrayList<Integer>[] data;

	public Graph(int vertices) {
		data = new ArrayList[vertices];
		for (int i = 0; i < data.length; i++) {
			data[i] = new ArrayList<Integer>();
		}
	}

	public void addEdge(int src, int dest) {
		data[src].add(dest);
		data[dest].add(src);
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
		ArrayList<Integer> neighbors = g.data[v];
		for (int n : neighbors) {
			if (!marked[n]) {
				dfs(g, n, marked, edgeTo);
				edgeTo[n] = v;
			}
		}
	}
	
	public static void pathTo(Graph g, int src) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		
	}

}
