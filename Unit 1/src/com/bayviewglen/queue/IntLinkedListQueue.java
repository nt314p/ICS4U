package com.bayviewglen.queue;

public class IntLinkedListQueue implements Queue{

	IntegerNode head;

	public IntLinkedListQueue() {
		head = null;
	}

	public void enqueue(Integer i) {
		if (head == null) {
			head = new IntegerNode(i, null);
		} else {
			getTail(head).setLink(new IntegerNode(i, null));
		}
	}

	public Integer dequeue() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		Integer temp = head.getData();
		head = head.getLink();
		return temp;
	}

	public Integer peek() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return head.getData();
	}

	public void clear() {
		head = null;
	}

	public boolean isEmpty() {
		return head == null;
	}

	private IntegerNode getTail(IntegerNode i) {
		if (i.getLink() == null)
			return i;
		return getTail(i.getLink());
	}

	class IntegerNode {
		private Integer data;
		private IntegerNode link;

		public IntegerNode(Integer data, IntegerNode link) {
			this.data = data;
			this.link = link;
		}

		public IntegerNode getLink() {
			return link;
		}

		public void setLink(IntegerNode link) {
			this.link = link;
		}

		public Integer getData() {
			return data;
		}

		public void setData(Integer data) {
			this.data = data;
		}
	}
}
