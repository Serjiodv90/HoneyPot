package monitorServer.parser;

import monitorServer.storage.ReportStorage;

public abstract class JsonToReportParser {

	ReportStorage reportStorage;
	private String jsonDirectoryPath;
	private String reportDirectoryPath;


	public abstract void parse();
	public abstract void storeReport();
	
	public String getJsonDirectoryPath() {
		return this.jsonDirectoryPath;
	}
	
	public String getReportDirectoryPath() {
		return this.reportDirectoryPath;
	}


}
