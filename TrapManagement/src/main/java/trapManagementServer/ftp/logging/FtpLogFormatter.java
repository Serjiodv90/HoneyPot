package trapManagementServer.ftp.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.springframework.context.support.StaticApplicationContext;

public class FtpLogFormatter extends Formatter {

	private static final String DATEFORMAT = "dd/MM/yyyy HH:mm:ss";
	
	@Override
	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer(1000);
		String date = getCurrentDate();
		
		buf.append("[ " + date + " ]");
		buf.append(" " + record.getLevel());
		buf.append("\tFTP --> ");
		buf.append(formatMessage(record));
		buf.append("\n");
		
		return buf.toString();
	}
	
	public static String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
		return dateFormat.format(new Date());
	}
	
	

}
