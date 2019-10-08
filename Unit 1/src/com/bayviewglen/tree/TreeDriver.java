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
			reader = new Scanner("5 3 1 4 9 7 6 8 14 10 17");
			while (reader.hasNextInt()) {
				bst.addInteger(reader.nextInt());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
		}
		System.out.println("inOrder");
		//bst.inOrderTraversal();
		bst.print();

		
		bst.deleteInteger(5);
		
		System.out.println("inOrder");
		//bst.inOrderTraversal();
		bst.print();
//		
//		System.out.println("preOrder");
//		bst.preOrderTraversal();
//		
//		System.out.println("postOrder");
//		bst.postOrderTraversal();
//		System.out.println("FINISHED!");
		//bst.print();
	}

}
