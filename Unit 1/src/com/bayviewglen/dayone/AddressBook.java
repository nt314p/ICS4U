package com.bayviewglen.dayone;

public class AddressBook {

	private Contact[] contacts;

	public AddressBook() {
		contacts = new Contact[0];
	}

	private void trimToSize(int size) {
		Contact[] newContacts = new Contact[size];
		for (int i = 0; i < size; i++) {
			if (contacts.length > i) {
				newContacts[i] = contacts[i];
			} else {
				newContacts[i] = null;
			}
		}
		contacts = newContacts;
	}

	private void trim() {
		int size = contacts.length;
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i] == null) {
				size--;
			}
		}
		Contact[] newContacts = new Contact[size];
		int j = 0;
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i] != null) {
				newContacts[j] = contacts[i];
				j++;
			}
		}
		contacts = newContacts;
	}

	public void addContact(Contact contact) {
		trimToSize(contacts.length + 1);
		contacts[contacts.length - 1] = contact;
	}

	public Contact getContactByFName(String firstName) {
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i].getFirstName().equalsIgnoreCase(firstName)) {
				return contacts[i];
			}
		}
		return null;
	}

	public Contact getContactByLName(String lastName) {
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i].getLastName().equalsIgnoreCase(lastName)) {
				return contacts[i];
			}
		}
		return null;
	}

	public Contact getContactByPhone(String phoneNumber) {
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i].getPhoneNumber().equals(phoneNumber)) {
				return contacts[i];
			}
		}
		return null;
	}

	public Contact getContact(Contact target) {
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i].equals(target)) {
				return contacts[i];
			}
		}
		return null;
	}

	private int getContactIndex(Contact target) {
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i].equals(target)) {
				return i;
			}
		}
		return -1;
	}

	public boolean editContactFName(Contact target, String newValue) {
		Contact edit = getContact(target);
		if (edit != null) {
			edit.setFirstName(newValue);
			return true;
		} else {
			return false;
		}
	}

	public boolean editContactLName(Contact target, String newValue) {
		Contact edit = getContact(target);
		if (edit != null) {
			edit.setLastName(newValue);
			return true;
		} else {
			return false;
		}
	}

	public boolean editContactPhone(Contact target, String newValue) {
		Contact edit = getContact(target);
		if (edit != null) {
			edit.setPhoneNumber(newValue);
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteContact(Contact target) {
		int index = getContactIndex(target);
		if (index != -1) {
			contacts[index] = null;
			trim();
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		String ret = "+-------------+-------------+--------------+\n| First Name  | Last Name   | Phone Number |\n";
		for (Contact c : contacts) {
			ret += "|-------------+-------------+--------------|\n";
			ret += c.toStringDisp() + "\n";
		}
		ret += "+-------------+-------------+--------------+\n";

		return ret;

	}

}
