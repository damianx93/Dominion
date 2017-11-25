package Handlers;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerConnectedMessageHandler extends ServerMessageHandler {
    private final String CLASSNAME = ServerMessageType.CONNECTED.toString();
    private String message = null;
    private MessageHandler superHandler;

    public ServerConnectedMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;
        //bring player into loginField

    }

    public void write(String message) {
        String tempMessage = addDelimiter(message);
        String newMessage = CLASSNAME + tempMessage;
        superHandler.write(newMessage);
    }

    public String getMessage() {
        return message;
    }
}
