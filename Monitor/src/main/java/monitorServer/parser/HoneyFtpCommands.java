package monitorServer.parser;

public enum HoneyFtpCommands {
	USER("USER", "user name: "),
	PASS("PASS", "user password: "),
	STOR("STOR", "is uploading file: "),
	DELE("DELE", "deleted file: "),
	RMD("RMD", "removed directory: "),
	RETR("RETR", "is downloading file : "),
	MDR("MDR", "created new directory: "),
	CWD("CWD", "changed working directory to: "),
	PWD("PWD", "prints current working directory: "),
	QUIT("QUIT", "disconnected")
	
	;
	
	
	private final String cmdName;
	private final String cmdDescription;
	
	
	
	HoneyFtpCommands(String cmdName, String description) {
		this.cmdName = cmdName;
		this.cmdDescription = description;
	}
	
	
	public boolean equals(String cmd) {
		if(this.cmdName.equalsIgnoreCase(cmd))
			return true;
		return false;
	}
	
	public String getCommandDescription() {
		return this.cmdDescription;
	}
	
	public static String getCommandDescription(String cmd) {
		for (HoneyFtpCommands command : HoneyFtpCommands.values()) 
			if(command.equals(cmd))
				return command.getCommandDescription();
		
		return "";
	}
	
	
	

}
