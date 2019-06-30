package monitorServer.parser;

import java.util.ArrayList;
import monitorServer.RequestFormat;
import monitorServer.storage.Report;

public interface JsonToReportParser {

	public Report parse(ArrayList<RequestFormat> reqArrList);

}
