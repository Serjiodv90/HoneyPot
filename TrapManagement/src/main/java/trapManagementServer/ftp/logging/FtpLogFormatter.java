package trapManagementServer.ftp.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FtpLogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer(1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy   HH:mm:ss");
		String date = dateFormat.format(new Date());
		
		buf.append("[ " + date + " ]");
		buf.append(" " + record.getLevel());
		buf.append("\tFTP --> ");
		buf.append(formatMessage(record));
		buf.append("\n");
		
		return buf.toString();
	}
	
	

}
