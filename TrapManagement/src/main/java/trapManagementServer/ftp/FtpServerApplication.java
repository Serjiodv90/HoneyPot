package trapManagementServer.ftp;


import java.io.IOException;



import org.apache.ftpserver.ftplet.FtpException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import trapManagementServer.ftp.model.HoneyFtpUtils;

@SpringBootApplication
public class FtpServerApplication {

	public static void main(String[] args) throws FtpException, IOException {

//		SpringApplication.run(FtpServerApplication.class, args);
		
		HoneyFtpUtils.startFtpServer();
		System.out.println("server is up!");
		System.out.println("Test the server:");

	}
	
	
	
	
	

}
