package monitorServer.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import monitorServer.RequestFormat;
import monitorServer.storage.Report;

@Component
public class FTP_Parser implements JsonToReportParser{

	private final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private final String timePatter = "(\\d\\d:\\d\\d:\\d\\d)";
	private Report report;

	//	@Bean
	//	@Autowired
	//	public void setReport(Report report) {
	//		this.report = report;
	//	}
	
	
	private String formatCommandDescription(String cmd) {
		return String.format("%-35s", cmd);
	}

	private String getCmdDescription(String request) {
		String [] cmdAndName = request.split(":")[2].split(",");	//contains the action description and the action target (e.x. file name) 
		String reqCmd = cmdAndName[0].trim();
		
		System.err.println("\nrequest: " + request);
		
		String cmdDescription = HoneyFtpCommands.getCommandDescription(reqCmd);
		if(!cmdDescription.isEmpty() && !cmdAndName[1].contains("null")) {
//			if()
			String cmdForReport = formatCommandDescription("Client " + cmdDescription);
			cmdForReport += "\t" + cmdAndName[1];
			
			System.err.println("FTP_Parser.getCmdDescription()\tcmdAndName: " + cmdForReport);

			return  cmdForReport ;
		}
		else 
			return "";
			
		
		
	}

	@Override
	public Report parse(ArrayList<RequestFormat> reqArrList) {

		Pattern ipPattern = Pattern.compile(this.ipv4Pattern, Pattern.CASE_INSENSITIVE);
		Pattern timePattern = Pattern.compile(this.timePatter, Pattern.CASE_INSENSITIVE);

		this.report = new Report(/*reqArrList.get(0).getDate(), */ServerType.FTP.name());

		//headline!
		String content = reqArrList.get(0).getDate().replaceAll("[\\[|\\]]", " ").replace("_", "\t").trim() + "\t" + this.report.getType() + "\n\n";
		Matcher ipMatcher = ipPattern.matcher(reqArrList.get(0).getRequest());
		String ipAddress = ""/*= matcher.group()*/;


		if(true == ipMatcher.find())
			ipAddress = ipMatcher.group();

		content += formatCommandDescription("New connection request from:") + "\t" + ipAddress;
		this.report.addContent(content);

		String[] userNameAndPwd = {"", ""};
		Matcher timeMatcher;

		reqArrList.remove(0);

		for (RequestFormat req : reqArrList) {
			String request = req.getRequest(); 

			timeMatcher = timePattern.matcher(req.getDate());
			String time = "";

			if(true == timeMatcher.find())
				time = timeMatcher.group();

			String command = "- " + time + "\t";

			ipMatcher = ipPattern.matcher(request);
			if(true == ipMatcher.find()) {
				if(request.contains("disconnected")) 
					command += "Client:\t" + ipMatcher.group() + ", has disconnected";
			}
			else {
				if(request.contains(HoneyFtpCommands.USER.name())) 
					userNameAndPwd[0] = request.split(",")[1].trim();

				else if(request.contains(HoneyFtpCommands.PASS.name())) {

					if(userNameAndPwd[1].isEmpty()) {	// first connection of this session
						userNameAndPwd[1] = request.split(",")[1].trim();
						command += "Client tries to login - user name: " + userNameAndPwd[0] + ", password: " + userNameAndPwd[1];
					}
					else {	// change of user's credential
						userNameAndPwd[1] = request.split(",")[1].trim();
						command += "Client tries to change credentials - user name: " + userNameAndPwd[0] + ", password: " + userNameAndPwd[1];
					}
					this.report.addContent(command);

				}
				else {
					String cmdDescription = getCmdDescription(request); 
					if(!cmdDescription.isEmpty()) {
						command += cmdDescription;
						this.report.addContent(command);
					}
				}
			}


		}

		System.out.println("Report: \n\n#####################################################################\n\n" + this.report);

		return this.report;
	}

	@Override
	public void storeReport() {
		// TODO Auto-generated method stub

	}

}
