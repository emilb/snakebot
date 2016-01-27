package se.cygni.snake.api.request;

import se.cygni.game.enums.Direction;
import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class RegisterMove extends GameMessage {

    private long gameTick;
    private Direction direction;

}
