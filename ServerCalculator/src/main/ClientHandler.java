package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread {

	BufferedReader clientInput = null;
	PrintStream clientOutput = null;
	Socket socketForCommunication = null;

	public ClientHandler(Socket socketForCommunication) {
		this.socketForCommunication = socketForCommunication;
	}

	@Override
	public void run() {

		try {

			clientInput = new BufferedReader(new InputStreamReader(socketForCommunication.getInputStream()));
			clientOutput = new PrintStream(socketForCommunication.getOutputStream());

			String entry = null;
			String operation = null;
			double firstNumber = 0, secondNumber = 0, result = 0;
			boolean exit = false;

			while (true) {

				do {
					clientOutput.println("Enter first number: ");
					entry = clientInput.readLine();

					if (!Control.isInputNumbersOk(entry))
						clientOutput.println("You should enter a number!");
				} while (!Control.isInputNumbersOk(entry));

				clientOutput.println("OK");

				if (!entry.equals("exit"))
					firstNumber = Double.parseDouble(entry);
				else {
					clientOutput.println("Goodbye :)");
					exit = true;
				}

				if (exit)
					break;

				do {
					clientOutput.println("Enter second number: ");
					entry = clientInput.readLine();

					if (!Control.isInputNumbersOk(entry))
						clientOutput.println("You should enter a number!");

				} while (!Control.isInputNumbersOk(entry));

				clientOutput.println("OK");

				if (!entry.equals("exit"))
					secondNumber = Double.parseDouble(entry);
				else {
					clientOutput.println("Goodbye! :)");
					exit = true;
				}

				do {
					clientOutput.println("Enter operation: ");
					entry = clientInput.readLine();

					if (!Control.isInputSignOk(entry))
						clientOutput.println("You should enter a sign of operation!");

				} while (!Control.isInputSignOk(entry));

				clientOutput.println("OK");

				if (!entry.equals("exit"))
					operation = entry;
				else {
					clientOutput.println("Goodbye! :)");
					exit = true;
				}

				if (exit)
					break;

				if (Control.isDivideByZero(secondNumber, operation)) {
					clientOutput.println("It's not possible to divide by zero.");
				} else {
					clientOutput.println(Calculator.calculate(firstNumber, secondNumber, operation) + "");
				}
			}

			System.out.println("Client disconnected.");
			socketForCommunication.close();

		} catch (IOException e) {
			System.out.println("Problems with connection.");
		}
	}
}
