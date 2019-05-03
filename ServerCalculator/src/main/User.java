package main;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

public class User {

	public static boolean registerClient(String username, String password) {

		boolean exists = false;
		boolean done;

		LinkedList<Account> existingAccounts = null;
		JsonArray accountsJson = null;

		if (!username.equals("") && !password.equals("") && password.length() >= 8 && haveCapitalLetter(password)
				&& haveNumbers(password)) {

			Account client = new Account(username, password);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			try (FileReader reader = new FileReader("data/accounts.json")) {
				JsonArray existingAccountsJSON = gson.fromJson(reader, JsonArray.class);
				existingAccounts = Account.parseAccounts(existingAccountsJSON);

				for (int i = 0; i < existingAccounts.size(); i++) {
					if (existingAccounts.get(i).getUsername().equals(client.getUsername())) {
						exists = true;
						break;
					}
				}

				reader.close();

			} catch (Exception e) {
				exists = false;
			}

			if (existingAccounts == null) {
				existingAccounts = new LinkedList<Account>();
			}

			if (!exists) {
				existingAccounts.add(client);
				done = true;
			} else {
				done = false;
			}

			accountsJson = Account.serializeAccounts(existingAccounts);

			try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/accounts.json")))) {
				String accountsString = gson.toJson(accountsJson);

				out.println(accountsString + "\n");

				out.close();

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				done = false;
			}
		} else {
			done = false;
		}

		return done;
	}

	public static boolean haveCapitalLetter(String password) {

		for (int i = 0; i < password.length(); i++) {
			if (Character.isUpperCase(password.charAt(i)))
				return true;
		}

		return false;
	}

	public static boolean haveNumbers(String password) {

		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i)))
				return true;
		}

		return false;
	}

	public static boolean loginClient(String username, String password) {

		LinkedList<Account> existingAccounts = null;
		boolean exists = false;
		Account client = new Account(username, password);

		Gson gson = new GsonBuilder().create();

		try (FileReader reader = new FileReader("data/accounts.json")) {
			JsonArray existingAccountsJSON = gson.fromJson(reader, JsonArray.class);
			existingAccounts = Account.parseAccounts(existingAccountsJSON);

			for (int i = 0; i < existingAccounts.size(); i++) {
				if (existingAccounts.get(i).getUsername().equals(client.getUsername())
						&& existingAccounts.get(i).getPassword().equals(client.getPassword())) {
					exists = true;
					break;
				}
			}

			reader.close();

		} catch (Exception e) {
			exists = false;
		}

		if (existingAccounts == null) {
			exists = false;
		}

		if (exists && !password.equals("")) {
			return true;
		} else {
			return false;
		}

	}
}
