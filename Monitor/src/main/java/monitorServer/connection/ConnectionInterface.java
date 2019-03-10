package monitorServer.connection;

import java.net.ServerSocket;

import monitorServer.model.ServerConfiguration;

public class ConnectionInterface {
	
	private final int SOCKET = 5000;
	
	
	
	
	public void startServer() {
		
		try (ServerSocket serverSocket = new ServerSocket(this.SOCKET)) {
			System.out.println("Monitor server online!");
			
			while(true) {
				new ServerConfiguration(serverSocket.accept()).start();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
