package trapManagementServer.ftp.model;

public enum HoneyFtpCommands {
	USER("USER", "User name: "),
	PASS("PASS", "User password: "),
	STOR("STOR", "File upload: "),
	DELE("DELE", "Delete file: "),
	RMD("RMD", "Remove directory"),
	RETR("RETR", "File download: "),
	MDR("MDR", "Create new directory: "),
	CWD("CWD", "Change working directory to: "),
	PWD("PWD", "Print current working directory: "),
	QUIT("QUIT", "User disconnected")
	
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
	
	public String getCommandDescription(String cmd) {
		for (HoneyFtpCommands command : HoneyFtpCommands.values()) 
			if(command.equals(cmd))
				return command.getCommandDescription();
		
		return "";
	}
	

}
