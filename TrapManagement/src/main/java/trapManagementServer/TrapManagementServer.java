package trapManagementServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ftpserver.ftplet.FtpException;

import com.example.demo.MyServerJpaApplication;

import ftp.Main;

public class TrapManagementServer {

	public static void main(String[] args) {
		MyServerJpaApplication.main(args);

			//ExecutorService es = Executors.newSingleThreadExecutor();
			//es.execute(()->{
				try {
					Main.main(args);
				} catch (FtpException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//});

	}

}
