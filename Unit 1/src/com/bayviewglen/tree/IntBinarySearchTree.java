package com.bayviewglen.tree;

public class IntBinarySearchTree {

	private IntBinaryTreeNode root;

	public IntBinarySearchTree(int[] arr) {
		for (int i : arr) {
			addInteger(arr[i]);
		}
	}

	public IntBinarySearchTree() {
		root = null;
	}

	public void addInteger(Integer data) {
		if (root == null)
			root = new IntBinaryTreeNode(data);
		else
			addIntNode(root, data);
	}

	private void addIntNode(IntBinaryTreeNode node, Integer data) {
		if (data < node.getData()) { // left
			if (node.hasLeftChild()) {
				addIntNode(node.getLeftChild(), data);
			} else {
				node.setLeftChild(new IntBinaryTreeNode(data));
			}
		} else { // right
			if (node.hasRightChild()) {
				addIntNode(node.getRightChild(), data);
			} else {
				node.setRightChild(new IntBinaryTreeNode(data));
			}
		}
	}
	
	
	public void inOrderTraversal() {
		inOrderTraversal(root);
	}
	
	private void inOrderTraversal(IntBinaryTreeNode node) {
		if (node.hasLeftChild())
			inOrderTraversal(node.getLeftChild());
		System.out.println(node.getData());
		if (node.hasRightChild())
			inOrderTraversal(node.getRightChild());
	}

	public void preOrderTraversal() {
		preOrderTraversal(root);
	}
	
	private void preOrderTraversal(IntBinaryTreeNode node) {
		System.out.println(node.getData());
		if (node.hasLeftChild())
			preOrderTraversal(node.getLeftChild());
		if (node.hasRightChild())
			preOrderTraversal(node.getRightChild());
	}	
	
	public void postOrderTraversal() {
		postOrderTraversal(root);
	}
	
	private void postOrderTraversal(IntBinaryTreeNode node) {
		if (node.hasLeftChild())
			postOrderTraversal(node.getLeftChild());
		if (node.hasRightChild())
			postOrderTraversal(node.getRightChild());
		System.out.println(node.getData());
	}	
	
	// inOrder: left, root, right
	// preOrder: root, left, right
	// postOrder: left, right, root

}
