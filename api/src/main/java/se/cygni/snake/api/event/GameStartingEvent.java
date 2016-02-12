package se.cygni.snake.api.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class GameStartingEvent extends GameMessage {

    private final String gameId;
    private final int noofPlayers;
    private final int width;
    private final int height;

    @JsonCreator
    public GameStartingEvent(
            @JsonProperty("gameId") String gameId,
            @JsonProperty("noofPlayers") int noofPlayers,
            @JsonProperty("width") int width,
            @JsonProperty("height") int gameHeight) {

        this.gameId = gameId;
        this.noofPlayers = noofPlayers;
        this.width = width;
        this.height = gameHeight;
    }

    public String getGameId() {
        return gameId;
    }

    public int getNoofPlayers() {
        return noofPlayers;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
