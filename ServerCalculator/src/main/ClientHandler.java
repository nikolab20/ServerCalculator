package main;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler {

	BufferedReader clientInput = null;
	PrintStream clientOutput = null;
	Socket socketForCommunication = null;

	public ClientHandler(Socket socketForCommunication) {
		this.socketForCommunication = socketForCommunication;
	}
}
