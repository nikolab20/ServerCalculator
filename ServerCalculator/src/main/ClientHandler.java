package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import gui.ServerGUI;

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

			String operation = null;
			String first, second;
			double firstNumber = 0, secondNumber = 0;
			boolean exit = false;

			while (true) {

				first = clientInput.readLine();
				second = clientInput.readLine();
				operation = clientInput.readLine();

				if (!Control.isInputNumbersOk(first) || !Control.isInputNumbersOk(second))
					clientOutput.println("You should enter a number!");
				else
					clientOutput.println("OK");

				

				if (!first.equals("exit") && !second.equals("exit")) {
					firstNumber = Double.parseDouble(first);
					secondNumber = Double.parseDouble(second);
				} else {
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

			ServerGUI.clientDisconnetedMessage();
			socketForCommunication.close();

		} catch (IOException e) {
			ServerGUI.clientErrorMessage();
		}
	}
}
