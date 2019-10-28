package com.bayviewglen.graph;

import java.util.ArrayList;

import com.bayviewglen.graph.Graph.Edge;

public class PrimsAlgorithm {

	public static Graph minSpanningTree(Graph g) {
		Graph mst = new Graph(g.V());
		int mstV = 1; // how many vertices have been added
		ArrayList<Edge> unselected = new ArrayList<Edge>(); // list of unselected edges
		boolean[] marked = new boolean[g.V()]; // vertices that have been marked
		int currVertex = 0; // start at vertex 0
		while (mstV != mst.V()) {
			marked[currVertex] = true; // current vertex has edge to it so mark
			for (Edge e : g.adj(currVertex)) {
				if (!marked[e.dest()]) // adding edge only if it doesn't connect
					unselected.add(e); // to a vertex that has already been used
			}
			
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
		}
		return mst;
	}

}
