package trapManagementServer.monitorInterface;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import trapManagementServer.RequestFormat;

public class MonitorConnection {
	
	private RestTemplate rest = new RestTemplate();
	private String url = "http://localhost:8085/storeReport";
	
	private ArrayList<RequestFormat> arr = new ArrayList<RequestFormat>();
	
	public void sendJson(List<RequestFormat> reqArrList) {
		//arr.add(new RequestFormat("date", "request"));
		//RequestFormat m = new RequestFormat("date", "request");
		System.out.println("In try to connect");
		rest.postForObject(url, reqArrList, ResponseEntity.class);
	}
	
	

}
