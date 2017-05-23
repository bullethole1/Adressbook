/**
 * 
 */
package com.lund.adressbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Main class handles all the logic and commands in the program.
 */
public class Main {

	private static Addressbook addressbook;
	private static BufferedReader input;

	/**
	 * Entrypoint, Creates a new Addressbook and asks for where the file is or
	 * will be stored.
	 * 
	 * @param args
	 *            No arguments need to be supplied
	 */
	public static void main(String[] args) {

		System.out.print("Specify the path to the file you want to load: ");
		input = new BufferedReader(new InputStreamReader(System.in));
		try {
			addressbook = new Addressbook(input.readLine());
		} catch (IOException e) {
			// The path entered is invalid, exit the application
			System.out.println("Failed to load the adressbook");
			e.printStackTrace();
			System.exit(0);
		}

		readCommand();
	}

	/**
	 * The readCommand method reads the next command and executes it, this
	 * method should also be executed after the command is finished.
	 */
	private static void readCommand() {
		System.out.print("Enter your command: ");
		try {
			String command = input.readLine();
			if (command.equalsIgnoreCase("list")) {
				list();
			} else if (command.equalsIgnoreCase("create")) {
				create();
			} else if (command.equalsIgnoreCase("delete")) {
				delete();
			} else if (command.equalsIgnoreCase("search")) {
				search();
			} else if (command.equalsIgnoreCase("exit")) {
				System.exit(0);
			} else {
				System.out.println("Unknown command, try again!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		readCommand(); // Repeat until the user types exit
	}

	/**
	 * Prints the list of all contacts
	 */
	private static void list() {
		ArrayList<Person> contactList = addressbook.getContacts();
		if (contactList.isEmpty()) {
			System.out.println("There are no contacts here");
			return;
		}

		System.out.println("Name\tPhone Number");
		for (Person person : contactList) {
			System.out.println(person.getName() + "\t"
					+ person.getPhoneNumber());
		}
	}

	/**
	 * Creates a new contact, if the contact already exists, we ask for
	 * permission to overwrite.
	 * 
	 * @throws IOException
	 */
	private static void create() throws IOException {
		System.out.print("Please enter a name for the new contact: ");
		String name = input.readLine();
		System.out.print("Please enter the contacts phone number: ");
		String phoneNumber = input.readLine();

		if (addressbook.getContact(name) == null) {
			addressbook.updateContact(name, phoneNumber);
		} else {
			System.out.print(name
					+ " already exists, do you wish to overwrite? (y/n)");
			if (input.readLine().equalsIgnoreCase("y")) {
				addressbook.updateContact(name, phoneNumber);
			}
		}
	}

	/**
	 * Searches and prints the contact information of a contact, if it exists.
	 * 
	 * @throws IOException
	 */
	private static void search() throws IOException {
		System.out.print("Search for contact named: ");
		String name = input.readLine();
		Person person = addressbook.getContact(name);
		if (person == null) {
			System.out.println("There is no person with that name");
		} else {
			System.out.println(person.getName() + "\t"
					+ person.getPhoneNumber());
		}
	}

	/**
	 * Delets a contact if it exists.
	 * 
	 * @throws IOException
	 */
	private static void delete() throws IOException {
		System.out.print("Delete contact named: ");
		String name = input.readLine();
		Person person = addressbook.getContact(name);
		if (person == null) {
			System.out.println("There is no person with that name");
		} else {
			addressbook.deleteContact(name);
			System.out.println(name + " Successfully deleted");
		}

	}

}
