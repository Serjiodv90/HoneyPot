package smtp.app.model;

import java.io.IOException;
import java.io.InputStream;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.TooMuchDataException;

import smtp.app.logging.SmtpLoggerManager;

public class MessageHandlerImpl implements MessageHandler {

	
private MessageContext messageContext;
private SmtpLoggerManager loggerManager;
	
	public MessageHandlerImpl() {
	}

	public MessageHandlerImpl (MessageContext context) {
		this.messageContext = context; 
		String clientIp = this.messageContext.getRemoteAddress().toString();
		this.loggerManager = new SmtpLoggerManager();
		this.loggerManager.onConnect(clientIp);
	}
	
	@Override
	public void from(String from) throws RejectException {
		this.loggerManager.mailFrom(from);
	}

	@Override
	public void recipient(String recipient) throws RejectException {
		this.loggerManager.rcptTo(recipient);
	}

	@Override
	public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
		this.loggerManager.data(data);
	}

	@Override
	public void done() {
		this.loggerManager.done();
	}

}
