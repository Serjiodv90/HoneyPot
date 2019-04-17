package trapManagementServer;


import java.io.IOException;
import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import trapManagementServer.ftp.FtpServerApplication;
import trapManagementServer.http.HttpServerApplication;

//@SpringBootApplication
//@ComponentScan({"trapManagementServer", "trapManagementServer.ftp"})
public class TrapManagementServer {

	public static void main(String[] args) {

//		SpringApplication.run(TrapManagementServer.class, args);
		HttpServerApplication.main(args);
		try {
			FtpServerApplication.main(args);
		} catch (FtpException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
