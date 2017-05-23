package com.lund.adressbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Addressbook {

	private ArrayList<Person> persons;
	private String path;

	/**
	 * Each instance of the Adressbook object is linked to a file where is saves
	 * its data. When created it either loads a file if it exists or creates an
	 * empty file to use.
	 * 
	 * @param filePath
	 *            The file path to load or create, either absolute or relative
	 *            search path.
	 */
	public Addressbook(String filePath) {
		this.path = filePath;

		BufferedReader reader;
		persons = new ArrayList<Person>(20); // Sets the initial capacity to 20,
												// this is only optional and we
												// only get a small performance
												// boost.

		try {
			File adressFile = new File(this.path);
			if (adressFile.exists()) {
				// If the file exists we try to read it
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(this.path)));

				String line;
				while ((line = reader.readLine()) != null) {
					// We loop through each line in the file found and parse it.
					String[] parsedData = new String[2];
					parsedData = line.split(";");
					persons.add(new Person(parsedData[0], parsedData[1]));
				}

				// When no more lines are found we make sure to close the file.
				reader.close();
			} else {
				// If the file is not found we try to create a new empty file.
				adressFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the contact if the name in the argument is present. If a name
	 * isn't present we create a contact with the new name.
	 * 
	 * @param name
	 *            The name of the contact to update or create
	 * @param phoneNumber
	 *            The phone number for that contact
	 */
	public void updateContact(String name, String phoneNumber) {
		Person person = getContact(name); // Search for the contact
		if (person != null) {
			// If a contact was found we simply update it with the new property
			// values.
			person.setName(name);
			person.setPhoneNumber(phoneNumber);
		} else {
			// If a contact wasn't found we create a new contact.
			person = new Person(name, phoneNumber);
			persons.add(person);
		}

		// Save the new/updated contact to file
		saveAdressbook();
	}

	/**
	 * Deletes a contact with the exact same name as supplied.
	 */
	public void deleteContact(String name) {
		// Do a foreach to find if any contacts name matches the supplied name.
		for (Person person : persons) {
			if (person.getName().equals(name)) {
				// A contact was found, delete him, save and return to close the
				// running of the methid.
				persons.remove(person);
				saveAdressbook();
				return;
			}
		}
	}

	/**
	 * Gets all contacts from the file non-filtered.
	 * 
	 * @return All contacts as an ArrayList
	 */
	public ArrayList<Person> getContacts() {
		return persons;
	}

	/**
	 * Gets the contact from the file that has the exact same match as the name
	 * supplied. Only one contact can be returned.
	 * 
	 * @param name
	 *            The name of the contact you want to get
	 * @return The contact, null if no contacts were found
	 */
	public Person getContact(String name) {
		for (Person person : persons) {
			if (person.getName().equals(name))
				return person;
		}
		return null; // There is no user with that name, so return null
	}

	/**
	 * Saves the contacts to the file supplied when created the Adressbook.
	 * Always do this when you have changed the persons object.
	 */
	private void saveAdressbook() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(this.path)));
			for (Person person : persons) {
				writer.write(person.getName() + ";" + person.getPhoneNumber()
						+ "\n"); // Adds a semicolon between the name and the
									// number, Don't forget the newline!
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
