package com.bayviewglen.queue;

public class IntArrayQueue implements Queue{
	
	private Integer[] data;
	private int firstIndex;
	private int lastIndex;
	
	public IntArrayQueue() {
		data = new Integer[1000];
		firstIndex = 0;
		lastIndex = 0;
	}
	
	public void enqueue(Integer i) {
		data[lastIndex++] = i;		
	}
	public Integer dequeue() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[firstIndex++];
	}
	public Integer peek() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return data[firstIndex];
	}
	public void clear() {
		firstIndex = lastIndex = 0;
	}
	public boolean isEmpty() {
		return lastIndex - firstIndex == 0;
	}

}
