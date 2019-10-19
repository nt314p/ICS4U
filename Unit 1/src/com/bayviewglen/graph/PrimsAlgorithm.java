package com.bayviewglen.graph;

import java.util.ArrayList;

import com.bayviewglen.graph.Graph.Edge;

public class PrimsAlgorithm {

	public static Graph minSpanningTree(Graph g) {
		Graph mst = new Graph(g.V());
		int mstV = 0; // keep track of the number of vertices in the mst
		int distance[] = new int[g.V()]; // distance[v] is distance from vert v to vert 0
		boolean marked[] = new boolean[g.V()];
		for (int i = 0; i < distance.length; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		


	}

	public static int minVertex(int[] arr) {
		int minVertex = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < arr[minVertex])
				minVertex = i;
		}
		return minVertex;
	}

}
