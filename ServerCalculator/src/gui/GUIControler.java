package gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.ClientHandler;

public class GUIControler {

	static Socket socketForCommunication = null;
	static ServerSocket serverSocket = null;
	static String text = "";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void startServer(JTextArea jtaServer) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(9000);

					while (true) {
						text += "Waiting for a connection...\n";
						jtaServer.setText(text);
						socketForCommunication = serverSocket.accept();
						text += "Connection established.\n";
						jtaServer.setText(text);

						ClientHandler client = new ClientHandler(socketForCommunication);

						client.start();
					}
				} catch (IOException e) {
					text += "Problems with socket!\n";
					jtaServer.setText(text);
				}
			}
		}).start();
	}
}
