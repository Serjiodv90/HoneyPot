package smtp.app.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import smtp.app.connection.DateFormatter;


public class SmtpLoggerFormatter extends Formatter{

	
	
	@Override
	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer(1000);
		String date = DateFormatter.getCurrentDateTimeForLog();

		
		buf.append("[ " + date + " ]");
		buf.append(" " + record.getLevel());
		buf.append(formatMessage(record));
		buf.append("\n");
		
		return buf.toString();
	}

}
