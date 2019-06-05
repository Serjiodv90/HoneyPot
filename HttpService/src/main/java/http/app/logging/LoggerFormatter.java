package http.app.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import http.app.connections.DateFormatter;

public class LoggerFormatter extends Formatter {
	
//	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
//        sb.append(DateFormatter.getCurrentDateTimeForLog());
		sb.append("[ " + DateFormatter.getCurrentDateTimeForLog() + " ]");
//        sb.append(" ");
		sb.append(" " + record.getLevel());
//        sb.append(record.getMessage());
//		sb.append("\tHTTP --> ");
		sb.append(formatMessage(record));
		sb.append("\n");
        return sb.toString();
	}
	
//	private String currentTime(){
//        return LocalDateTime.now().format(dateFormatter);
//    }

}
