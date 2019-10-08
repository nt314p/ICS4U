package com.bayviewglen.tree;

public class IntBinaryTreeNode {
	
	private IntBinaryTreeNode leftChild;
	private IntBinaryTreeNode rightChild;
	private Integer data;
	
	public IntBinaryTreeNode(Integer i) {
		leftChild = null;
		rightChild = null;
		data = i;
	}

	public IntBinaryTreeNode(IntBinaryTreeNode leftChild, IntBinaryTreeNode rightChild, Integer data) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.data = data;
	}

	public IntBinaryTreeNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(IntBinaryTreeNode leftChild) {
		this.leftChild = leftChild;
	}
	
	public boolean hasLeftChild() {
		return leftChild != null;
	}

	public IntBinaryTreeNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(IntBinaryTreeNode rightChild) {
		this.rightChild = rightChild;
	}
	
	public boolean hasRightChild() {
		return rightChild != null;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}
	
	public boolean hasChild(int direction) {
		if (direction == -1)
			return hasLeftChild();
		else if (direction == 1)
			return hasRightChild();
		return false;
	}
	
	public IntBinaryTreeNode getChild(int direction) {
		if (direction == -1)
			return getLeftChild();
		else if (direction == 1)
			return getRightChild();
		return null;
	}
	
	public void setChild(IntBinaryTreeNode child, int direction) {
		if (direction == -1)
			setLeftChild(child);
		else if (direction == 1)
			setRightChild(child);
	}

}
