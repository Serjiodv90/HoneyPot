package trapManagementServer.monitorInterface;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MonitorConnection {
	
	private static final String FILETOSEND = "D:/java/Udemy/EchoClient/HTTPLog.json";

	//TODO: change the method so it can get JSON file to send
	public void sendJsonLogToMonitor() {
		
		//create socket in client side
				try(Socket socket = new Socket("localhost", 5000)) {	//supply the server ip, and server port
					
					socket.setSoTimeout(55000); 	//set timeout for server's answer
					
					OutputStream out = socket.getOutputStream();
					BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter stringToEcho = new PrintWriter(out, true);
					
					File sendFile = new File(FILETOSEND);
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sendFile));
					int sendFileSize = (int)sendFile.length();
					byte[] fileToBytes = new byte[sendFileSize];
					bis.read(fileToBytes, 0, sendFileSize);
					
					//send first the size of the file
					stringToEcho.println(String.valueOf(sendFileSize));
					stringToEcho.flush();
					
					System.out.println("Sending file to server");
					out.write(fileToBytes, 0, sendFileSize);
					out.flush();
					
					System.out.println("message from server: " + echoes.readLine());
					stringToEcho.println("exit");
					
					
//					Scanner scanner = new Scanner(System.in);
//					String echoString, response;
//					
//					do {
//						System.out.println("Enter string to be echoed: ");
//						echoString = scanner.nextLine();
//						stringToEcho.println(echoString);	//send to server
//						if(!echoString.equals("exit")) {
//							response = echoes.readLine();	//read the data from the server
//							System.out.println(response);
//						}
//					} while(!echoString.equals("exit"));
					
				} catch(SocketTimeoutException e) {
					System.out.println("The socket timed out");
				} catch (IOException e) {
					System.out.println("Client Error: " + e.getMessage());
				}
	}

}
