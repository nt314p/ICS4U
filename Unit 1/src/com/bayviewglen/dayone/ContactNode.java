package com.bayviewglen.dayone;

public class ContactNode {

	private Contact data;
	private ContactNode link;

	public ContactNode(Contact data, ContactNode link) {
		this.data = data;
		this.link = link;
	}

	public ContactNode getLink() {
		return link;
	}

	public void setLink(ContactNode link) {
		this.link = link;
	}

	public Contact getData() {
		return data;
	}

	public void setData(Contact data) {
		this.data = data;
	}

	public boolean isTail() {
		return link == null;
	}

	public ContactNode getTail() {
		if (isTail()) {
			return this;
		}
		return link.getTail();
	}

	public int getLenghtToTail() {
		if (isTail()) return 1;
		return link.getLenghtToTail(1);
	}

	private int getLenghtToTail(int len) {
		if (isTail()) return len + 1;
		return link.getLenghtToTail(len + 1);
	}

}
