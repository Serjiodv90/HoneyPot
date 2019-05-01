package trapManagementServer.smtp.model;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

public class MessageHandlerFactoryImpl implements MessageHandlerFactory {

	@Override
	public MessageHandler create(MessageContext ctx) {
		return new MessageHandlerImpl(ctx);
	}

}
