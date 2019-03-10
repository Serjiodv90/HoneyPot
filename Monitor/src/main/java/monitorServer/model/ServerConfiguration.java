package monitorServer.model;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import monitorServer.parser.JsonToReportParser;


public class ServerConfiguration extends Thread{
	
	private Socket socket;
	private BufferedOutputStream bos;
	private String fileToStoreLocation = "D:/java/Udemy/EchoServer";
	private JsonToReportParser parser;
	
	
	public ServerConfiguration (Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("SERVER --> new connection");
		try {
			InputStream is = socket.getInputStream();
			BufferedReader input = new BufferedReader(new InputStreamReader(is));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			
			int fileSize;
			int currentFileByteLoc = 0;
			int dataAmoutRecieved;
			byte[] fileInputBytes;
						
			while(true) {
				String echoString = input.readLine();	//recieve the size of the file first
				System.out.println("Recieved client input: " + echoString);
				System.out.flush();
				
				fileSize = Integer.parseInt(echoString);
				fileInputBytes = new byte[fileSize];
				bos = new BufferedOutputStream(new FileOutputStream(new File("FileSendTest.json")));	//where to write the data
					//get the stream of the file
				
				
				if(echoString.equals("exit"))
					break;
				System.out.println("Server port: " + socket.getPort() + " Server ip: " + socket.getInetAddress());
				
				do {
					dataAmoutRecieved = is.read(fileInputBytes, currentFileByteLoc, fileSize - currentFileByteLoc);
					currentFileByteLoc += dataAmoutRecieved > 0 ? dataAmoutRecieved : 0;
					System.out.println("dataAmoutRecieved: " + dataAmoutRecieved);
				}while(dataAmoutRecieved >0);
				
				//write the content of the received file
				bos.write(fileInputBytes, 0, currentFileByteLoc);
				bos.flush();

				
				output.println("File recieved!");
			}
			
		} catch(IOException e) {
			System.out.println("Oops: " + e.getMessage());
		} finally {
			try {
				socket.close();
			} catch(IOException e) {
				//oh well...
			}
		}
	}
	
	
	//for class diagram, this method should store files from run method, invoke to parser according to the protocol
	private void storeFile(String protocol, BufferedOutputStream file) {
		
	}

}
