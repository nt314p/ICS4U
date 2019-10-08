package com.bayviewglen.tree;

import java.util.ArrayList;

public class IntBinarySearchTree {

	private IntBinaryTreeNode root;

	public IntBinarySearchTree(int[] arr) {
		build(arr);
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

	public void clear() {
		root = null;
	}

	public void build(int[] arr) {
		for (int i : arr) {
			addInteger(i);
		}
	}

	public boolean hasInteger(Integer data) {
		return getIntNode(root, data) != null;
	}

	public boolean deleteIntegerRebuild(Integer data) {
		IntBinaryTreeNode node = getIntNode(root, data);
		if (node == null)
			return false;
		ArrayList<Integer> arr = new ArrayList<Integer>();
		toArray(root, arr);
		arr.remove(data);
		clear();
		int[] intArr = new int[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			intArr[i] = arr.get(i);
		}
		build(intArr);
		return true;
	}

	public boolean deleteInteger(Integer data) {

		IntBinaryTreeNode node = getIntNode(root, data);
		IntBinaryTreeNode grandchild, grandparent;
		boolean hasChildren = false;
		for (int i = -1; i <= 1; i += 2) {
			if (node.hasChild(i)) {
				hasChildren = true;
				grandchild = getGrandchild(node.getChild(i), -i);
				grandparent = getParentNode(root, grandchild);
				grandparent.setChild(grandchild.getChild(i), -i);
			}
		}
		grandparent = getParentNode(root, node);
		if (!hasChildren) {
			if (grandparent == null) {
				root = null;
			}
			for (int i = -1; i <= 1; i += 2) {
				if (grandparent.getChild(i) == node)
					grandparent.setChild(null, i);
			}
		}
		return true;
	}

	private IntBinaryTreeNode getParentNode(IntBinaryTreeNode node, IntBinaryTreeNode child) {
		if ((child.getData() - node.getLeftChild().getData()) * (child.getData() - node.getRightChild().getData()) == 0)
			return node;
		if (child.getData() < node.getData())
			return getParentNode(node.getLeftChild(), child);
		else if (child.getData() > node.getData())
			return getParentNode(node.getRightChild(), child);
		return null;
	}

	private IntBinaryTreeNode getIntNode(IntBinaryTreeNode node, Integer data) {
		if (node == null)
			return null;
		if (data < node.getData()) {
			return getIntNode(node.getLeftChild(), data);
		} else if (data > node.getData()) {
			return getIntNode(node.getRightChild(), data);
		}
		return node;
	}

	private IntBinaryTreeNode getRightGrandchild(IntBinaryTreeNode node) {
		return (node.hasRightChild() ? getRightGrandchild(node.getRightChild()) : node);
	}

	private IntBinaryTreeNode getLeftGrandchild(IntBinaryTreeNode node) {
		return (node.hasLeftChild() ? getLeftGrandchild(node.getLeftChild()) : node);
	}

	private IntBinaryTreeNode getGrandchild(IntBinaryTreeNode node, int direction) { // -1:left;1:right
		if (direction == -1)
			return (node.hasLeftChild() ? getGrandchild(node.getLeftChild(), -1) : node);
		else if (direction == 1)
			return (node.hasRightChild() ? getGrandchild(node.getRightChild(), 1) : node);
		return null;
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

	private void toArray(IntBinaryTreeNode node, ArrayList<Integer> arr) {
		if (node.hasLeftChild())
			toArray(node.getLeftChild(), arr);
		if (node.hasRightChild())
			toArray(node.getRightChild(), arr);
		arr.add(node.getData());
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

	public void print() {
		String[] s = new String[7];
		for (int i = 0; i < s.length; i++) {
			s[i] = "";
		}
		print(root, 0, s);
		for (String str : s) {
			System.out.println(str);
		}
	}

	private void print(IntBinaryTreeNode node, int level, String[] s) {
		if (node.hasLeftChild())
			print(node.getLeftChild(), level + 1, s);
		s[level] += String.format(" %3d", node.getData());
		for (int i = 0; i < level; i++) {
			s[level] += " ";
		}
		if (node.hasRightChild())
			print(node.getRightChild(), level + 1, s);
	}

}
