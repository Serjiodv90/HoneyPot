package trapManagement.app.trapsGenerator;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TrapsGenerator {
	
	public void plantUsernameInHttpLoginPage() throws IOException {
		File input = new File("/resources/traps/afekaLogin.html");
		Document doc = Jsoup.parse(input, "UTF-8");
		
		Element username = doc.getElementById("input_1");
		username.attr("value", "username");
		
		System.out.println(doc.toString());
	}

}
