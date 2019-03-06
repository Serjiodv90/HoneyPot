package trapManagementServer;

public class RequestFormat {
	
	private String date;
	private String request;
	
	public RequestFormat(String date, String request) {
		this.date = date;
		this.request = request;
	}
	
	@Override
	public String toString() {
		return "Date: " + this.date + " Request: " + request;
	}

}
