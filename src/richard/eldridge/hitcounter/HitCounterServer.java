package richard.eldridge.hitcounter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HitCounterServer {

	public static final int PORT_NUMBER = 63457;

	private ServerSocket serverSocket;

	private Socket socket;

	private Integer count = 0;

	public HitCounterServer() {
		System.out.println("Server is running");
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			while (true) {
				socket = serverSocket.accept();
				PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
				System.out.println("New connection started");
				count++;
				toClient.println(count);
			}
		} catch (IOException e) {
			System.out.println("An exception was caught when trying to listen on port: " + PORT_NUMBER);
			e.printStackTrace();
		} finally { 
			try {
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (Exception e) {
				//oh dear, nothing to see here...
			}
		}
	}

	public static void main(String[] args) {
		new HitCounterServer();
	}

}
