package com.bayviewglen.postfix;

public class TermStack {
	private Term data[];
	private int manyItems;

	public TermStack() {
		data = new Term[100];
		manyItems = 0;
	}

	public void push(Term data) {
		this.data[manyItems++] = data;
	}

	public Term pop() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[--manyItems];
	}

	public boolean isEmpty() {
		return manyItems == 0;
	}

	public Term peek() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[manyItems - 1];
	}
}
