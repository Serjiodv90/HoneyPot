package http.app.logging;


import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import http.app.connections.DateFormatter;

public class LoggerFormatter extends Formatter {
	
	
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ " + DateFormatter.getCurrentDateTimeForLog() + " ]");
		sb.append(" " + record.getLevel());
		sb.append(formatMessage(record));
		sb.append("\n");
        return sb.toString();
	}

}
