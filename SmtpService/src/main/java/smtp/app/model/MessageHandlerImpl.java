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
		// TODO Auto-generated constructor stub
	}

	public MessageHandlerImpl (MessageContext context) {
		System.out.println("MessageHandlerImpl.MessageHandlerImpl()");
		this.messageContext = context; 
		String clientIp = this.messageContext.getRemoteAddress().toString();
		System.out.println("Sender addrs: " + clientIp);
		this.loggerManager = new SmtpLoggerManager();
		this.loggerManager.onConnect(clientIp);
	}
	
	@Override
	public void from(String from) throws RejectException {
		System.out.println("From: " + from);
		this.loggerManager.mailFrom(from);
	}

	@Override
	public void recipient(String recipient) throws RejectException {
		System.out.println("Recipient: " + recipient);
		this.loggerManager.rcptTo(recipient);
	}

	@Override
	public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
		this.loggerManager.data(data);
	}

	@Override
	public void done() {
		System.out.println("That's it!!!");
		this.loggerManager.done();
	}

}
