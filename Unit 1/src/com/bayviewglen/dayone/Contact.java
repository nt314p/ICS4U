package com.bayviewglen.dayone;

public class Contact implements Comparable<Contact> {

	private String firstName;
	private String lastName;
	private String phoneNumber;

	public Contact(String firstName, String lastName, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public Contact(String firstName) {
		this.firstName = firstName;
		this.lastName = "";
		this.phoneNumber = "";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int compareTo(Contact c) {
		return this.firstName.compareTo(c.firstName);
	}

	public boolean equals(Contact c) {
		return firstName.equals(c.firstName) && lastName.equals(c.lastName) && phoneNumber.equals(c.phoneNumber);
	}
	
	public String toStringDisp() {
		String p1 = String.format("| %-11s ", firstName) + String.format("| %-11s ", lastName);
		String p2 = p1 + String.format("| %-13s|", phoneNumber);
		return p2;
	}
	
	public String toString() {
		return firstName + " " + lastName + ", " + phoneNumber;
	}
	
	

}
