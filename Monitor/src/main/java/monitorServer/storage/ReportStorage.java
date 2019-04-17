package monitorServer.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import monitorServer.RequestFormat;
import monitorServer.dal.ReportDao;
import monitorServer.parser.FTP_Parser;
import monitorServer.parser.HTTP_Parser;
import monitorServer.parser.JsonToReportParser;
import monitorServer.parser.SMTP_Parser;
import monitorServer.parser.ServerType;

@Service
public class ReportStorage {

	private JsonToReportParser parser;
	private ReportDao reportDao;
	
	@Autowired
	public ReportStorage(JsonToReportParser parser, ReportDao reportDao) {
		this.parser = parser;
		this.reportDao = reportDao;
	}
	
	
	
	//find with server sent json, and init the parser accordingly
	private void setParser(String request) {
		String typePattern = "";
		
		for (ServerType type : ServerType.values()) {
			typePattern += type.name() + "|";
		}
		
		typePattern = typePattern.substring(0, typePattern.length() - 1);
		
		Pattern pattern = Pattern.compile(typePattern, Pattern.CASE_INSENSITIVE);
		Matcher typeMatcher = pattern.matcher(request);
		
		String serverReqType = "";
		if(true == typeMatcher.find()) {
			serverReqType = typeMatcher.group();
		}
		
		if(serverReqType.equalsIgnoreCase(ServerType.HTTP.name()))
			this.parser = new HTTP_Parser();
		else if(serverReqType.equalsIgnoreCase(ServerType.FTP.name()))
				this.parser = new FTP_Parser();
		else if (serverReqType.equalsIgnoreCase(ServerType.SMTP.name()))
				this.parser = new SMTP_Parser();
	}
	
	@Async
	public Report createReport(ArrayList<RequestFormat> reqArrList) {
		setParser(reqArrList.get(0).getRequest());
		Report report = this.parser.parse(reqArrList);
		return this.reportDao.save(report);
	}
	
	
	
	

	
	
	
	
	

}
