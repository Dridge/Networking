package richard.eldridge.networking;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CheckPortAndIpAddress extends JFrame {
	private static final long serialVersionUID = 1L;

	public JTextArea infoArea = new JTextArea(6, 25);

	public CheckPortAndIpAddress() {
		initGUI();
		setTitle("Check Port and IP Address");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			//Port number
			//creating a server socket at port 0 connects to a random available port
			ServerSocket serverSocket  = new ServerSocket(0);
			int port = serverSocket.getLocalPort();
			serverSocket.close();
			infoArea.setText("Available port: " + port);

			//Private IP Address
			InetAddress host = InetAddress.getLocalHost();
			String hostName = host.getHostName();
			String privateIPAddress = host.getHostAddress();
			infoArea.append("\n\nHost Name: " + hostName);
			infoArea.append("\nPrivate IP Address: " + privateIPAddress);
			
			//Public IP Address
			URL url = new URL("http://checkip.amazonaws.com/");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String publicIpAddress = in.readLine();
			in.close();
			infoArea.append("\n\nPublic IP Address: " + publicIpAddress);
		} catch (Exception e) {
			infoArea.append("\n" + e.getMessage());
		}
	}

	private void initGUI() {
		add(infoArea, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {
			// do nothing
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CheckPortAndIpAddress();
			}
		});
	}

}
