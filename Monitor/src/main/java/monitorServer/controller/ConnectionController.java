package monitorServer.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import monitorServer.RequestFormat;
import monitorServer.storage.ReportStorage;


@RestController
public class ConnectionController {

	private ReportStorage reportStorage;

	@Autowired
	public void setReportStorage(ReportStorage reportStorage) {
		this.reportStorage = reportStorage;
	}

	@RequestMapping(path="/storeReport",
					method=RequestMethod.POST,
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces=MediaType.APPLICATION_JSON_VALUE)
	public RequestFormat[] getJsonFile(@RequestBody RequestFormat[] reqArrList) {
		System.out.println("In monitor");
		//		System.out.println(reqArrList);
		this.reportStorage.createReport(new ArrayList<>(Arrays.asList(reqArrList)));
		return reqArrList;
	}


}
