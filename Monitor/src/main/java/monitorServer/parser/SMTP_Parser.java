package monitorServer.parser;

import java.util.ArrayList;

import monitorServer.RequestFormat;
import monitorServer.storage.Report;

public class SMTP_Parser implements JsonToReportParser{

	
	
	@Override
	public Report parse(ArrayList<RequestFormat> reqArrList) {
		Report report = new Report(ServerType.SMTP.name());
		
		for (RequestFormat requestLine : reqArrList) 
			report.addContent(requestLine.getRequest());
		
		return report;
		
	}

}
