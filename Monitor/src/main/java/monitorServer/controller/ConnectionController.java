package monitorServer.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import monitorServer.RequestFormat;
import monitorServer.storage.Report;
import monitorServer.storage.ReportStorage;


@RestController
public class ConnectionController {

	private ReportStorage reportStorage;

	@Autowired
	public  ConnectionController(ReportStorage reportStorage) {
		this.reportStorage = reportStorage;
	}
	
	@RequestMapping(
			path="/storeReport",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public RequestFormat[] getJsonFile(@RequestBody RequestFormat[] reqArrList) {
		this.reportStorage.createReport(new ArrayList<>(Arrays.asList(reqArrList)));
		return reqArrList;
	}


	@RequestMapping(
			path="/reports",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Report[] getAllReports(
			@RequestParam(name="fromDate", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date fromDate,
			@RequestParam(name="type", required=false) String type) throws ParseException {
		
		if(fromDate != null && type != null && !type.isEmpty())
			return this.reportStorage.getAllReportsByDateAndType(fromDate, type).toArray(new Report[0]);
		else if (fromDate != null && (type == null || type.isEmpty()))
			return this.reportStorage.getAllReportsByDate(fromDate).toArray(new Report[0]);
		else if (fromDate == null && (type != null && !type.isEmpty())) {
			return  this.reportStorage.getAllReportsByType(type).toArray(new Report[0]);
		}
		else
			return this.reportStorage.getAllReports().toArray(new Report[0]);
	}

	


}
