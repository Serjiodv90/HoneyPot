package trapManagementServer;

public interface JsonSave {
	public final static String JSON_PATH ="C:\\Users\\DELL\\Documents\\workspace-sts-3.9.7.RELEASE\\TrapManagement\\src\\JsonFiles";
	
	public static String getJsonPath() {
		return JSON_PATH;
	}
	
	public abstract void logToJson();
}
