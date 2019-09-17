package com.bayviewglen.ds;

public class IntLinkedListStack implements Stack {

	IntegerNode head;

	public IntLinkedListStack() {
		head = null;
	}

	public void push(Integer data) {
		if (isEmpty()) {
			head = new IntegerNode(data, null);
		} else {
			head = new IntegerNode(data, head);
		}
	}

	public Integer pop() {
		Integer x = head.getData();
		if (isEmpty()){
			throw new IllegalStateException("BAD");
		} else if (head.getLink() != null) {
			head = head.getLink();
		} else {
			head = null;
		}
		return x;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public Integer peek() {
		if (isEmpty())
			throw new IllegalStateException("BAD");
		return head.getData();
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
