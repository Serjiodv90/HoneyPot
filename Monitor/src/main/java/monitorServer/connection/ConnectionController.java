package monitorServer.connection;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import monitorServer.RequestFormat;


@RestController
public class ConnectionController {
	
	@RequestMapping(path="/getJson",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void getJsonFile(@RequestBody ArrayList<RequestFormat> reqArrList) {
		System.out.println("In monitor");
		System.out.println(reqArrList);
	}
}
