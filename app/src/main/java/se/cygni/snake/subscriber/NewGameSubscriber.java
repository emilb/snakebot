package se.cygni.snake.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cygni.game.World;
import se.cygni.game.enums.Direction;
import se.cygni.snake.engine.SnakeGameEngine;

@Component
public class NewGameSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(NewGameSubscriber.class);

    @Autowired
    private SnakeGameEngine engine;

    public World createNewGame(int height, int width) {
        return engine.createNewGame(height, width);
    }

    public String addPlayer(String name) {
        return engine.addPlayer(name);
    }

    public World movePlayer(String id, Direction direction) {
       return engine.movePlayer(id, direction);
    }

    public World tick() throws Exception {
        return engine.tick();
    }

    public void startGame() {
        engine.startGame();
    }
}
