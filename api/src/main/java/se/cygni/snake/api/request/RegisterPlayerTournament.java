package se.cygni.snake.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class RegisterPlayerTournament extends GameMessage {

    private final String playerName;
    private final String color;

    @JsonCreator
    public RegisterPlayerTournament(
            @JsonProperty("playerName") String playerName,
            @JsonProperty("color") String color) {

        this.playerName = playerName;
        this.color = color;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getColor() {
        return color;
    }

}