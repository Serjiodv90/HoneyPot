package trapManagementServer.smtp.model;

import java.io.IOException;
import java.io.InputStream;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.TooMuchDataException;

public class MessageHandlerImpl implements MessageHandler {

	
private MessageContext messageContext;
	
	public MessageHandlerImpl() {
		// TODO Auto-generated constructor stub
	}

	public MessageHandlerImpl (MessageContext context) {
		System.out.println("MessageHandlerImpl.MessageHandlerImpl()");
		this.messageContext = context;
		System.out.println("Sender addrs: " + this.messageContext.getRemoteAddress());
	}
	
	@Override
	public void from(String from) throws RejectException {
		System.out.println("From: " + from);

	}

	@Override
	public void recipient(String recipient) throws RejectException {
		System.out.println("Recipient: " + recipient);

	}

	@Override
	public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
		System.out.println("Data: " + data);
	}

	@Override
	public void done() {
		System.out.println("That's it!!!");

	}

}
