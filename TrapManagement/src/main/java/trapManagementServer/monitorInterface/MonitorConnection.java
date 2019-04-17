package trapManagementServer.monitorInterface;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import trapManagementServer.RequestFormat;

public class MonitorConnection {
	
	private RestTemplate rest = new RestTemplate();
	private String url = "http://localhost:8085/storeReport";
	
	private ArrayList<RequestFormat> arr = new ArrayList<RequestFormat>();
	
	public void sendJson(List<RequestFormat> reqArrList) {
		//arr.add(new RequestFormat("date", "request"));
		//RequestFormat m = new RequestFormat("date", "request");
		System.out.println("In try to connect");
		rest.postForObject(url, reqArrList, ResponseEntity.class);
	}
	
	/*private static final String FILETOSEND = "D:/java/Udemy/EchoClient/HTTPLog.json";

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
	}*/

}
