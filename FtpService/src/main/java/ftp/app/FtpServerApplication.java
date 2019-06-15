package ftp.app;


import java.io.IOException;



import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ftp.app.logic.HoneyFtpUtils;

@SpringBootApplication
public class FtpServerApplication {

	public static void main(String[] args) throws FtpException, IOException {

		SpringApplication.run(FtpServerApplication.class, args);
		
//		HoneyFtpUtils.startFtpServer();
//		System.out.println("server is up!");
//		System.out.println("Test the server:");

	}
	
	
	
	
	

}
