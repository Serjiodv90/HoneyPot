package trapManagement.app.trapsGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import trapManagement.app.model.fakeTrapUsers.FakeUser;

@Component
public class TrapsGenerator {
	
	private List<FakeUser> fakeUsers;
	private final String HTML_TRAP = "./src/main/resources/traps/afekaLogin.html";
	
	
	public TrapsGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public void setFakeUsers(List<FakeUser> users) {
		this.fakeUsers = users;
	}
	
	
//	@Async
	public void createTraps() {
		System.err.println("TrapsGenerator.createTraps()\nFake Users: " + this.fakeUsers + "\nThread: " + Thread.currentThread() + "\n");
	
		try {
			plantUsernameInHttpLoginPage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void plantUsernameInHttpLoginPage() throws IOException  {
	 	File htmlTrapFile = new File(this.HTML_TRAP);
	 	if(!htmlTrapFile.exists())
	 		htmlTrapFile.createNewFile();
	 	
//	 	File htmlTerapFile = new ClassPathResource(this.HTML_TRAP).getFile();
    	System.out.println("\nFILE: " + htmlTrapFile);
    	
		Document doc = Jsoup.parse(htmlTrapFile, "UTF-8");
		System.err.println("\n*********************************************\ndocument: " + doc + "\n");
		
		Element username = doc.getElementById("input_1");
		System.err.println("\n*********************************************\nUSERNAME: " + username + "\n");
		username.attr("value", "username");
		
		PrintWriter pw = new PrintWriter(htmlTrapFile);
		pw.print(doc.toString());
		System.out.println(doc.toString());
		pw.close();
		}
	}


