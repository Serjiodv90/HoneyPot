package monitorServer.parser;

import java.util.ArrayList;
import java.util.List;

import monitorServer.RequestFormat;
import monitorServer.storage.Report;
import monitorServer.storage.ReportStorage;

public interface JsonToReportParser {

//	ReportStorage reportStorage;
//	private final String JSONDIRECTORTYPATH = "target/Json_recieved";
//	private String reportDirectoryPath;


	public Report parse(List<RequestFormat> reqArrList);
	public void storeReport();
	
//	public String getJsonDirectoryPath() {
//		return this.JSONDIRECTORTYPATH;
//	}
//	
//	public String getReportDirectoryPath() {
//		return this.reportDirectoryPath;
//	}


}
