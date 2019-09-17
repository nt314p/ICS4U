package com.bayviewglen.ds;

public class IntArrayStack implements Stack {

	private Integer data[];
	private int manyItems;

	public IntArrayStack() {
		data = new Integer[1000];
		manyItems = 0;
	}

	public void push(Integer data) {
		this.data[manyItems++] = data;
	}

	public Integer pop() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[--manyItems];
	}

	public boolean isEmpty() {
		return manyItems == 0;
	}

	public Integer peek() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[manyItems - 1];
	}

}
