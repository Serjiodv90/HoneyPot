package http.app.connections;

public interface JsonSave {
	public final static String JSON_PATH ="C:\\Users\\DELL\\Documents\\Honeypot\\projects\\HoneyPot\\TrapManagement\\src\\JsonFiles";
	
	public static String getJsonPath() {
		return JSON_PATH;
	}
	
//	public abstract void logToJson();
	
	public abstract void registerObserver(JsonObserver obs);
	public abstract void notifyAllRegistered();
}
