package com.bayviewglen.tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TreeDriver {

	public static void main(String[] args) {
		IntBinarySearchTree bst = new IntBinarySearchTree();

		String filePath = "src/com/bayviewglen/tree/data.dat";
		Scanner reader;
		try {
			reader = new Scanner(new File(filePath));

			while (reader.hasNextInt()) {
				bst.addInteger(reader.nextInt());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("inOrder");
		bst.inOrderTraversal();
		
		System.out.println("preOrder");
		bst.preOrderTraversal();
		
		System.out.println("postOrder");
		bst.postOrderTraversal();
		System.out.println("FINISHED!");
	}

}
