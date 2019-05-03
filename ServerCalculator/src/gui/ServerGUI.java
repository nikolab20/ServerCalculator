package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem startItem;
	private JMenuItem exitItem;
	private JSeparator separator;
	private JScrollPane scrollPane;
	private static JTextArea jtaServer;
	private JPanel colorPanel;
	private JMenuItem stopItem;

	/**
	 * Create the frame.
	 */
	public ServerGUI() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIControler.exitApp(contentPane, jtaServer);
			}
		});
		setPreferredSize(new Dimension(130, 0));
		setMinimumSize(new Dimension(330, 200));
		setBounds(100, 100, 330, 200);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		contentPane.add(getColorPanel(), BorderLayout.EAST);
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
		}
		return menuBar;
	}

	private JMenu getMnFile() {
		if (mnFile == null) {
			mnFile = new JMenu("File");
			mnFile.add(getStartItem());
			mnFile.add(getStopItem());
			mnFile.add(getSeparator());
			mnFile.add(getExitItem());
		}
		return mnFile;
	}

	private JMenuItem getStartItem() {
		if (startItem == null) {
			startItem = new JMenuItem("Start server");
			startItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUIControler.startServer(jtaServer, colorPanel, startItem, stopItem);
				}
			});
		}
		return startItem;
	}

	private JMenuItem getExitItem() {
		if (exitItem == null) {
			exitItem = new JMenuItem("Exit");
			exitItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUIControler.exitApp(contentPane, jtaServer);
				}
			});
		}
		return exitItem;
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getJtaServer());
		}
		return scrollPane;
	}

	private JTextArea getJtaServer() {
		if (jtaServer == null) {
			jtaServer = new JTextArea();
		}
		return jtaServer;
	}

	private JPanel getColorPanel() {
		if (colorPanel == null) {
			colorPanel = new JPanel();
			colorPanel.setBackground(Color.RED);
		}
		return colorPanel;
	}

	private JMenuItem getStopItem() {
		if (stopItem == null) {
			stopItem = new JMenuItem("Stop server");
			stopItem.setEnabled(false);
			stopItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!GUIControler.serverSocket.isClosed()) {
						GUIControler.stopServer(jtaServer);
					}
					GUIControler.stopServer(jtaServer);
				}
			});
		}
		return stopItem;
	}

	public static void clientLoggedInMessage(String username) {
		jtaServer.append("User " + username + " logged in.\n");
	}

	public static void clientDisconnetedMessage(String username) {
		jtaServer.append("User " + username + " disconnected.\n");
	}

	public static void clientErrorMessage() {
		jtaServer.append("Problems with streams.\n");
	}
}
