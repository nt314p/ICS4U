package com.bayviewglen.dayone;

import java.util.Iterator;

public class ContactList implements Iterable<Contact> {

	private ContactNode head;

	public ContactList() {
		head = new ContactNode(null, null);
	}

	public void addContact(Contact contact) {
		if (head.getData() == null) {
			head.setData(contact);
		} else {
			head = new ContactNode(contact, head);
		}
	}

	private ContactNode getPrevContactNode(Contact contact) {
		for (ContactNode node = head; node != null; node = node.getLink()) {
			if (node.getLink().getData().equals(contact)) {
				return node;
			}
		}
		return null;
	}
	
	public Contact deleteContact(Contact target) {
		if (target == head.getData()) {
			head = head.getLink();
			if (head == null) {
				head = new ContactNode(null, null);
			}
			return target;
		}
		ContactNode prevContact = getPrevContactNode(target);
		ContactNode nextContact = prevContact.getLink().getLink();
		prevContact.setLink(nextContact);
		return target;
	}

	public Iterator<Contact> iterator() {
		return new ContactListIterator();
	}

	class ContactListIterator implements Iterator<Contact> {
		ContactNode node = head;

		public boolean hasNext() {
			return node != null && node.getData() != null;
		}

		public Contact next() {
			Contact temp = node.getData();
			node = node.getLink();
			return temp;
		}

		public void remove() {
			throw new UnsupportedOperationException("Not supported.");
		}
	}

}
