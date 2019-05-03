package main;

import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Account {
	String username;
	String password;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Account() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static JsonArray serializeAccounts(LinkedList<Account> accounts) {
		JsonArray accountsArray = new JsonArray();

		for (int i = 0; i < accounts.size(); i++) {
			Account a = accounts.get(i);

			JsonObject accountJson = new JsonObject();
			accountJson.addProperty("username", a.getUsername());
			accountJson.addProperty("password", a.getPassword());

			accountsArray.add(accountJson);
		}

		return accountsArray;
	}

	public static LinkedList<Account> parseAccounts(JsonArray accountsJson) {
		LinkedList<Account> accounts = new LinkedList<Account>();

		for (int i = 0; i < accountsJson.size(); i++) {
			JsonObject accountJson = (JsonObject) accountsJson.get(i);

			Account a = new Account();

			a.setUsername(accountJson.get("username").getAsString());
			a.setPassword(accountJson.get("password").getAsString());

			accounts.add(a);
		}

		return accounts;
	}

//	public static String getPassString(char[] password) {
//		String pass = "";
//
//		for (int i = 0; i < password.length; i++) {
//			pass = pass + password[i];
//		}
//
//		return pass;
//	}

	public String toString() {
		return "Account [username=" + username + ",\n password=" + password + "]\n";
	}

}
