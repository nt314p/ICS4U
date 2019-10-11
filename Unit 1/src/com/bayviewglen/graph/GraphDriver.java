package com.bayviewglen.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File("src\\com\\bayviewglen\\graph\\data.txt");
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
		}

		Graph g = new Graph(reader.nextInt()); // first line is the # of vertices
		reader.nextInt(); // throw away edges
		
		while (reader.hasNextInt()) {
			g.addEdge(reader.nextInt(), reader.nextInt());
		}	
		reader.close();
		
		Graph.depthFirstSearch(g, 0);
	}

}
