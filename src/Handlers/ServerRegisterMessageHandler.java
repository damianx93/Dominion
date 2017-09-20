package Handlers;

import DataBasePackage.Database;
import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;

/**
 * Created by Tim on 13.09.2017.
 */
public class ServerRegisterMessageHandler extends MessageHandler {
    private String message =null;
    private final String CLASSNAME = ServerMessageType.REGISTER.toString();

    public ServerRegisterMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        message=msgIn;
        String returnMessage = null;

        if (register(msgIn)) {//if register successful
            returnMessage = "register successful";
            //send message to okMessageHandler with first altering the original msgIn and then handler.HandleMessage...
        } else { //if register failed
            returnMessage = "Username already in use";
        }


    }

    public boolean register(String msgIn) {
        boolean registerSuccessful = false;

        String userName = splitMessage(msgIn, 4);
        String password = splitMessage(msgIn, 5);

        registerSuccessful = Database.getDatabase().insert(userName, password); //only registers if the name does not exist

        return registerSuccessful;
    }

    public void write(String outMessage) {
        String tempMessage = addDelimiter(outMessage);
        String newMessage = CLASSNAME + tempMessage;
        super.write(newMessage);
    }
    public String getMessage(){
        return message;
    }
}
