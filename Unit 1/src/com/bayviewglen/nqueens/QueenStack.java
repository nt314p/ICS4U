package com.bayviewglen.nqueens;

public class QueenStack {
	private Queen data[];
	private int manyItems;

	public QueenStack() {
		data = new Queen[1000];
		manyItems = 0;
	}

	public void push(Queen data) {
		this.data[manyItems++] = data;
	}

	public Queen pop() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[--manyItems];
	}

	public boolean isEmpty() {
		return manyItems == 0;
	}

	public Queen peek() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[manyItems - 1];
	}
}
