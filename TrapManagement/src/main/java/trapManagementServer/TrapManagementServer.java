package trapManagementServer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EventListener;

import javax.servlet.http.HttpSessionListener;

import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//import com.example.demo.aop.HttpRequestsInterceptor;
import com.google.gson.Gson;

import trapManagementServer.ftp.Main;
import trapManagementServer.http.HttpServerApplication;

public class TrapManagementServer {

	public static void main(String[] args) {
		JsonFilesManager manager = new JsonFilesManager();
		HttpServerApplication.main(args);
		try {
			Main.main(args);
		} catch (FtpException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.register(ApplicationSessionConfiguration.class);
//		ctx.refresh();
//		ServletListenerRegistrationBean<SessionCounter> myBean = ctx.getBean(ServletListenerRegistrationBean.class);
		
		manager.manage();




	}

}
