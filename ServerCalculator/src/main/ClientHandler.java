package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import gui.ServerGUI;

public class ClientHandler extends Thread {

	BufferedReader fromClient = null;
	PrintStream forClient = null;
	Socket socketForCommunication = null;

	public ClientHandler(Socket socketForCommunication) {
		this.socketForCommunication = socketForCommunication;
	}

	@Override
	public void run() {

		String username = "guest";
		String password = null;

		try {

			fromClient = new BufferedReader(new InputStreamReader(socketForCommunication.getInputStream()));
			forClient = new PrintStream(socketForCommunication.getOutputStream());

			String operation = null;
			String first, second;
			double firstNumber = 0, secondNumber = 0;
			boolean exit = false;
			boolean guest = false;
			String input = null;

			do {
				input = fromClient.readLine();

				if (input.equals("/exit")) {

					exit = true;

				} else if (input.equals("/login")) {

					boolean loggedIn = false;

					do {
						username = fromClient.readLine();
						if (username.equals("/exit")) {
							exit = true;
							break;
						}

						password = fromClient.readLine();
						if (password.equals("/exit")) {
							exit = true;
							break;
						}

						loggedIn = User.loginClient(username, password);

						if (username.equals("")) {
							forClient.println("You must enter a username!");
						} else if (password.equals("")) {
							forClient.println("You must enter a password!");
						} else if (!loggedIn) {
							forClient.println("User does not exist!");
						} else
							forClient.println("OK");

					} while (username.equals("") || password.equals("") || !loggedIn);

				} else if (input.equals("/reg")) {

					boolean registered = false;

					do {
						username = fromClient.readLine();
						if (username.equals("/exit")) {
							exit = true;
							break;
						}

						password = fromClient.readLine();
						if (password.equals("/exit")) {
							exit = true;
							break;
						}

						registered = User.registerClient(username, password);

						if (username.equals("")) {
							forClient.println("You must enter a username!");
						} else if (password.equals("")) {
							forClient.println("You must enter a password!");
						} else if (password.length() < 8) {
							forClient.println("Password must have more then 8 characters!");
						} else if (!User.haveCapitalLetter(password)) {
							forClient.println("Password must have at least one upper case letter!");
						} else if (!User.haveNumbers(password)) {
							forClient.println("Password must have at least one digit!");
						} else if (!registered) {
							forClient.println("User does not exist!");
						} else {
							forClient.println("OK");
						}

					} while (username.equals("") || password.equals("") || !registered || password.length() < 8
							|| !User.haveCapitalLetter(password) || !User.haveNumbers(password));

				} else if (input.equals("/guest")) {
					guest = true;
				}

			} while (!input.equals("/exit") && !input.equals("/login") && !input.equals("/reg")
					&& !input.equals("/guest"));

			if (!exit) {
				while (true) {

					first = fromClient.readLine();
					second = fromClient.readLine();
					operation = fromClient.readLine();

					if (!Control.isInputNumbersOk(first) || !Control.isInputNumbersOk(second))
						forClient.println("You should enter a number!");
					else
						forClient.println("OK");

					if (!first.equals("exit") && !second.equals("exit")) {
						firstNumber = Double.parseDouble(first);
						secondNumber = Double.parseDouble(second);
					} else {
						exit = true;
					}

					if (exit)
						break;

					if (Control.isDivideByZero(secondNumber, operation)) {
						forClient.println("It's not possible to divide by zero.");
					} else {
						forClient.println(Calculator.calculate(firstNumber, secondNumber, operation) + "");
					}
				}
			}
			
			ServerGUI.clientDisconnetedMessage();
			socketForCommunication.close();

		} catch (IOException e) {
			ServerGUI.clientErrorMessage();
		}
	}
}
