package se.cygni.snake.client;

import se.cygni.snake.api.event.GameEndedEvent;
import se.cygni.snake.api.event.GameStartingEvent;
import se.cygni.snake.api.event.MapUpdateEvent;
import se.cygni.snake.api.event.SnakeDeadEvent;

public interface SnakeClient {

    public void onMapUpdate(MapUpdateEvent mapUpdateEvent);

    public void onSnakeDead(SnakeDeadEvent snakeDeadEvent);

    public void onGameEnded(GameEndedEvent gameEndedEvent);

    public void onGameStarting(GameStartingEvent gameStartingEvent);

}
