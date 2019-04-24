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

			String entry;
			int firstNumber, secondNumber, result;

			while (true) {

				clientOutput.println("Enter first number: ");
				entry = clientInput.readLine();
				if (!entry.equals("exit"))
					firstNumber = Integer.parseInt(entry);
				else {
					clientOutput.println("Goodbye :)");
					break;
				}

				clientOutput.println("Enter second number: ");
				entry = clientInput.readLine();
				if (!entry.equals("exit"))
					secondNumber = Integer.parseInt(entry);
				else {
					clientOutput.println("Goodbye :)");
					break;
				}
				
				result = firstNumber + secondNumber;
				clientOutput.println(result + "");
			}
			
			socketForCommunication.close();

		} catch (IOException e) {
			System.out.println("Problems with connection.");
		}
	}
}
