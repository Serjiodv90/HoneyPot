package smtp.app.connection;

public class RequestFormat {
	
	private String date;
	private String request;
	
	public RequestFormat() {
	}
	
	public RequestFormat(String date, String request) {
		this.date = date;
		this.request = request;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
	@Override
	public String toString() {
		return "Date: " + this.date + " Request: " + request;
	}

}
