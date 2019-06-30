package monitorServer.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monitorServer.RequestFormat;
import monitorServer.storage.Report;

public class HTTP_Parser implements JsonToReportParser{

	private String httpRequestPattern = "GET|POST|DELETE|UPDATE|PATCH"; 
	private final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private final String timePatter = "(\\d\\d:\\d\\d:\\d\\d)";
	private final String userNameAndPwdPattern = "user|password"; 
	private Report report;
	
	
	
	private String createRequestDescription(String request) {
		Pattern httpRequestPattern = Pattern.compile(this.httpRequestPattern, Pattern.CASE_INSENSITIVE);
		Matcher httpReqMatcher = httpRequestPattern.matcher(request);
		Pattern loginPattern = Pattern.compile(this.userNameAndPwdPattern, Pattern.CASE_INSENSITIVE);
		Matcher loginMatcher = loginPattern.matcher(request);
		String httpReqMethod = "";
		
		if(true == httpReqMatcher.find()) 
			httpReqMethod = httpReqMatcher.group();
		
		String[] reqBody = request.trim().split(httpReqMethod);
		if (reqBody.length < 2)
			return "";
		
		return  "Method - " + httpReqMethod + " -->\t " + (loginMatcher.find() ? ("Client tries to connect - " + reqBody[1]) : reqBody[1]);
		
		
	}
	
	@Override
	public Report parse(ArrayList<RequestFormat> reqArrList) {
		Pattern ipPattern = Pattern.compile(this.ipv4Pattern, Pattern.CASE_INSENSITIVE);
		Pattern timePattern = Pattern.compile(this.timePatter, Pattern.CASE_INSENSITIVE);
		
		this.report = new Report(ServerType.HTTP.name());
		
		//headline!
		String content = reqArrList.get(0).getDate().replaceAll("[\\[|\\]]", " ").replace("_", "\t").trim() + "\t" + this.report.getType() + "\n\n";
		Matcher ipMatcher = ipPattern.matcher(reqArrList.get(0).getRequest());
		String ipAddress = ""/*= matcher.group()*/;
		if(true == ipMatcher.find())
			ipAddress = ipMatcher.group();

		content += String.format("%-35s","New connection request from:" + "\t" + ipAddress);
		this.report.addContent(content);
		
		Matcher timeMatcher;
		reqArrList.remove(0);
		
		for (RequestFormat req : reqArrList) {
			String request = req.getRequest();
			
			timeMatcher = timePattern.matcher(req.getDate());
			String time = "";

			if(true == timeMatcher.find())
				time = timeMatcher.group();

			String command = "- " + time + "\t";
			String reqDescription = createRequestDescription(request);
			if(!reqDescription.isEmpty()) {
				command += reqDescription;
				this.report.addContent(command);
			}
		}
		
		return this.report;
		
	}



}
