package monitorServer.parser;

import java.util.ArrayList;
import java.util.List;

import monitorServer.RequestFormat;
import monitorServer.storage.Report;
import monitorServer.storage.ReportStorage;

public interface JsonToReportParser {

	public Report parse(ArrayList<RequestFormat> reqArrList);

}
