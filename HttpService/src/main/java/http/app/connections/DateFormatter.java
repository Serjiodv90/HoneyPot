package http.app.connections;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
	
	public static String getCurrentDateTimeForFile() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("[ dd-MM-yyyy_HH-mm-ss ]");
		return  dateFormat.format(new Date());
	}
	
	public static String getCurrentDateTimeForLog() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(new Date());
	}

}
