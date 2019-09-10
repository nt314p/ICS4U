package com.bayviewglen.dayone;

import java.util.Scanner;

public class AddressBookDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AddressBook contacts = new AddressBook();
		Contact c = new Contact("Nick", "Tong", "647");
		Contact d = new Contact("Ethan", "Eblaze", "648");
		Contact e = new Contact("Sonya", "Verma", "649");

		contacts.addContact(c);
		contacts.addContact(d);
		contacts.addContact(e);
		System.out.println(contacts);

		System.out.println(contacts.getContactByFName("Nick"));
		contacts.deleteContact(c);
		System.out.println(contacts.getContactByFName("Nick"));
		System.out.println(contacts);

		System.out.println(contacts.editContactPhone(e, "650"));
		System.out.println(contacts);
		contacts.deleteContact(d);
		contacts.deleteContact(e);
		contacts.addContact(c);
		System.out.println(contacts);
		

		Scanner reader = new Scanner(System.in);
		Contact selected = null;

		while (true) {
			System.out.println("Selected: " + selected);

			String cmd = reader.nextLine();
			switch (cmd) {
			case "new":
				System.out.println("First Name:");
				Contact con = new Contact(reader.nextLine());
				System.out.println("Last Name:");
				con.setLastName(reader.nextLine());
				System.out.println("Phone Number:");
				con.setPhoneNumber(reader.nextLine());
				contacts.addContact(con);
				break;
			case "disp":
				System.out.println(contacts);
				break;
			case "sel":
				System.out.println("Find by (f, l, p)");
				switch (reader.nextLine()) {
				case "f":
					System.out.println("First Name:");
					selected = contacts.getContactByFName(reader.nextLine());
					break;
				case "l":
					System.out.println("Last Name:");
					selected = contacts.getContactByLName(reader.nextLine());
					break;
				case "p":
					System.out.println("Phone Number:");
					selected = contacts.getContactByPhone(reader.nextLine());
					break;
				}
				break;
			case "dsel":
				selected = null;
				break;
			case "edit":
				System.out.println("Editing: " + selected);
				System.out.println("Edit by (f, l, p)");
				switch (reader.nextLine()) {
				case "f":
					System.out.println("First Name:");
					selected.setFirstName(reader.nextLine());
					break;
				case "l":
					System.out.println("Last Name:");
					selected.setLastName(reader.nextLine());
					break;
				case "p":
					System.out.println("Phone Number:");
					selected.setPhoneNumber(reader.nextLine());
					break;
				}
				break;

			case "del":
				if (selected == null) {
					System.out.println("Nothing is selected");
				} else {
					contacts.deleteContact(selected);
					System.out.println("Selected contact deleted");
				}
				break;
			}
		}
	}

}
