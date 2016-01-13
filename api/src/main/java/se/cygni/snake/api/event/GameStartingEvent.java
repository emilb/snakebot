package se.cygni.snake.api.event;

import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class GameStartingEvent extends GameMessage {

    private String gameId;
    private int noofPlayers;
    private int gameWidth;
    private int gameHeight;

}
