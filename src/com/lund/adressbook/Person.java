package com.lund.adressbook;

public class Person {
	private String name;
	private String phoneNumber;
	
	/**
	 * A person must always have a name and a phone number.
	 * So this must be set in the constructor.
	 * 
	 * @param name The name of the contact
	 * @param phoneNumber The phone number of the contact
	 */
	public Person(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the name of the person
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the phone number of the person
	 * @return Returns the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Updates the name of the person
	 * @param name The new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Updates the phone number of the person
	 * @param phoneNumber The new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
