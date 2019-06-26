package ftp.app;


import java.io.IOException;



import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FtpServiceApplication {

	public static void main(String[] args) throws FtpException, IOException {

		SpringApplication.run(FtpServiceApplication.class, args);
		

	}
	
	
	
	
	

}
