package se.cygni.snake.api.event;

import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class WorldUpdateEvent extends GameMessage {

    private long gameTick;
    private long gameId;

    // Include world state, active players, their lengths etc
}
