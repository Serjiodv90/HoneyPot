package trapManagement.app.trapsGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import trapManagement.app.model.fakeTrapUsers.FakeUser;

@Component
public class TrapsGenerator {
	
	private List<FakeUser> fakeUsers;
	
	public TrapsGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public void setFakeUsers(List<FakeUser> users) {
		this.fakeUsers = users;
	}
	
	
	
	public void createTraps() {
		System.err.println("TrapsGenerator.createTraps()\nFake Users: " + this.fakeUsers);
	}
	
	
	private void plantUsernameInHttpLoginPage() throws IOException {
		File input = new File("/resources/traps/afekaLogin.html");
		Document doc = Jsoup.parse(input, "UTF-8");
		
		Element username = doc.getElementById("input_1");
		username.attr("value", "username");
		
		System.out.println(doc.toString());
	}

}
