package se.cygni.snake.api.event;

import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class GameStartingEvent extends GameMessage {

    private String gameId;
    private int noofPlayers;
    private int gameWidth;
    private int gameHeight;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getNoofPlayers() {
        return noofPlayers;
    }

    public void setNoofPlayers(int noofPlayers) {
        this.noofPlayers = noofPlayers;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }
}
