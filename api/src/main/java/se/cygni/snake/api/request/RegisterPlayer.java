package se.cygni.snake.api.request;

import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class RegisterPlayer extends GameMessage {

    private String playerName;
    private String color;
    private char headChar;
    private char tailChar;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public char getHeadChar() {
        return headChar;
    }

    public void setHeadChar(char headChar) {
        this.headChar = headChar;
    }

    public char getTailChar() {
        return tailChar;
    }

    public void setTailChar(char tailChar) {
        this.tailChar = tailChar;
    }
}
