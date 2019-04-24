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
			int firstNumber = 0, secondNumber = 0, result = 0;
			boolean exit = false;

			while (true) {

				do {
					clientOutput.println("Enter first number: ");
					entry = clientInput.readLine();
					
					if(entry.equals("") || entry == null)
						clientOutput.println("You should enter a number!");
				} while (entry.equals("") || entry == null);
				
				clientOutput.println("OK");

				if (!entry.equals("exit"))
					firstNumber = Integer.parseInt(entry);
				else {
					clientOutput.println("Goodbye :)");
					exit = true;
				}

				if (exit)
					break;

				do {
					clientOutput.println("Enter second number: ");
					entry = clientInput.readLine();
					
					if(entry.equals("") || entry == null)
						clientOutput.println("You should enter a number!");
					
				} while (entry.equals("") || entry == null);
				
				clientOutput.println("OK");
				

				if (!entry.equals("exit"))
					secondNumber = Integer.parseInt(entry);
				else {
					clientOutput.println("Goodbye! :)");
					exit = true;
				}

				clientOutput.println("Enter operation: ");
				entry = clientInput.readLine();
				if (!entry.equals("exit"))
					operation = entry;
				else {
					clientOutput.println("Goodbye! :)");
					exit = true;
				}

				if (exit)
					break;

				clientOutput.println(Calculator.calculate(firstNumber, secondNumber, operation) + "");
			}

			socketForCommunication.close();

		} catch (IOException e) {
			System.out.println("Problems with connection.");
		}
	}
}
