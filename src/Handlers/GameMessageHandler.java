package Handlers;

import Database.Database;
import GameLogic.Game;
import Server.Player;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Tim on 23.08.2017.
 */
public class GameMessageHandler extends MessageHandler implements Observer {
    private MessageHandler superHandler;

    private final String CLASSNAME = MessageType.GAME.toString();


    public GameMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, MAIN_HANDLER_INDEX);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameMessageHandler() {

    }

    @Override
    public void handleMessage(String message, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        //get the game name --> String gameName = splitMessage(message,3);
        //GameHandlers game = GameManager.getGame(gameName);
        //returnMessage = game.handleMessage()

        //die unteren muessen zu damiano in das jeweilige GameHandlers
        String subHandler = splitMessage(message, SUB_HANDLER_INDEX);

        Player player = socketPlayerHashMap.get(getClientSocket().getInetAddress());
        if (gameList.get(player) == null || subHandler.equalsIgnoreCase("ENDGAME")) {
            MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
            handler.handleMessage(message, this);
        } else {
            gameList.get(player).readMessage(message);
        }


    }

    public void write(String message,Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage,privateMessage);
    }


    @Override
    public void update(Observable o, Object arg) {
        String response = (String) arg;
        if (response.contains("endgame")) {
            endGame();
        }
        write(response, false);
        //todo what to do with the response?
    }

    private void endGame() {

        topFiveUpdate();
        //showLobby();
        deleteGameFromList();
    }

    private void topFiveUpdate() {

        //Database.getDatabase().updateAfterGame();
    }

    private void deleteGameFromList() {
        Player player = socketPlayerHashMap.get(getClientSocket().getInetAddress());
        ArrayList<Player> listToRemove = getKeyByValue(gameList, gameList.get(player));
        for (int i = 0; listToRemove.size() > i; i++) {
            gameList.remove(listToRemove.get(i));
        }
    }

    //sobald nachricht hierhin kommt muss geprüft werden ob game = null ist. wenn ja dann wird diese method ausgelöst?

    public Socket getClientSocket() {
        return superHandler.getClientSocket();
    }

}
