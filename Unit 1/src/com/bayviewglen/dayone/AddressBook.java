package com.bayviewglen.dayone;

public class AddressBook {

	private ContactList contacts;

	public AddressBook() {
		contacts = new ContactList();
	}
	
	public void addContact(Contact contact) {
		contacts.addContact(contact);
	}
	
	public void deleteContact(Contact contact) {
		contacts.deleteContact(contact);
	}

	public Contact getContactByFName(String firstName) {
		for (Contact c : contacts) {
			if (c.getFirstName().equalsIgnoreCase(firstName)) {
				return c;
			}
		}
		return null;
	}

	public Contact getContactByLName(String lastName) {
		for (Contact c : contacts) {
			if (c.getLastName().equalsIgnoreCase(lastName)) {
				return c;
			}
		}
		return null;
	}

	public Contact getContactByPhone(String phoneNumber) {
		for (Contact c : contacts) {
			if (c.getPhoneNumber().equals(phoneNumber)) {
				return c;
			}
		}
		return null;
	}

	public Contact getContact(Contact target) {
		for (Contact c : contacts) {
			if (c.equals(target)) {
				return c;
			}
		}
		return null;
	}

	public boolean editContactFName(Contact target, String newValue) {
		Contact edit = getContact(target);
		if (edit != null) {
			edit.setFirstName(newValue);
			return true;
		}
		return false;
	}

	public boolean editContactLName(Contact target, String newValue) {
		Contact edit = getContact(target);
		if (edit != null) {
			edit.setLastName(newValue);
			return true;
		}
		return false;
	}

	public boolean editContactPhone(Contact target, String newValue) {
		Contact edit = getContact(target);
		if (edit != null) {
			edit.setPhoneNumber(newValue);
			return true;
		}
		return false;
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
