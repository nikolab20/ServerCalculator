package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	static Socket socketForCommunication = null;
	static ServerSocket serverSocket = null;

	public static void main(String[] args) {

		try {
			serverSocket = new ServerSocket(9000);

			while (true) {
				System.out.println("Waiting for a connection...");
				socketForCommunication = serverSocket.accept();
				System.out.println("Connection established.");
				
				ClientHandler client = new ClientHandler(socketForCommunication);
			}
		} catch (IOException e) {
			System.out.println("Problems with socket!");
		}
	}
}
