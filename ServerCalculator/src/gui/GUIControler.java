package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.ClientHandler;

public class GUIControler {
	
	public static ServerGUI mainWindow;

	static Socket socketForCommunication = null;
	static ServerSocket serverSocket = null;
	static String text = "";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow = new ServerGUI();
					mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void startServer(JTextArea jtaServer, JPanel colorPanel, JMenuItem startItem, JMenuItem stopItem) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(9000);

					while (true) {
						jtaServer.append("Waiting for a connection...\n");
						colorPanel.setBackground(Color.GREEN);
						startItem.setEnabled(false);
						stopItem.setEnabled(true);
						socketForCommunication = serverSocket.accept();
						jtaServer.append("Connection established.\n");

						ClientHandler client = new ClientHandler(socketForCommunication);

						client.start();
					}
				} catch (SocketException e) {
					if (serverSocket != null && serverSocket.isClosed()) {
						jtaServer.append("Connection closed.\n");
						startItem.setEnabled(true);
						stopItem.setEnabled(false);
						colorPanel.setBackground(Color.RED);
					} else if (serverSocket == null)
						jtaServer.append("Port is unreachable.\n");
				} catch (IOException e) {
					startItem.setEnabled(true);
					stopItem.setEnabled(false);
					jtaServer.append("Accept failed.\n");
				}
			}
		}).start();
	}

	public static void stopServer(JTextArea jtaServer) {
		try {
			serverSocket.close();
		} catch (IOException e) {
			jtaServer.append("Sockets are already closed.\n");
		}
	}

	public static void exitApp(JPanel contentPane, JTextArea jtaServer) {
		int option = JOptionPane.showConfirmDialog(mainWindow, "Do you really want to quit?",
				"Exit", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) 
			System.exit(0);
	}
}
