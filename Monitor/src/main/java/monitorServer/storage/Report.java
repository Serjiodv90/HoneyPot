package monitorServer.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="REPORTS")
public class Report {

	private String id; 
	private Date date;
	private String type;	//HTTP, FTP, SMTP
	private List<String> content;
	
	public Report() {
		this.content = new ArrayList<>();
	}
	
	public Report(String type) {
		this();
		this.date = new Date();
		this.type = type;
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
	
	public void addContent(String additionalContant) {
		this.content.add(additionalContant);
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		
		String retString = "";
		for (String string : content) {
			retString += string + "\n";
		}
		
		return retString;
	}
	
	
	
	
	
	
}
