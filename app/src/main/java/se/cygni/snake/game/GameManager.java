package se.cygni.snake.game;


import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameManager {

    @Autowired
    EventBus globalEventBus;

    public Game createTrainingGame() {
        return new Game(new GameFeatures(), globalEventBus);
    }

    public Game createGame(GameFeatures gameFeatures) {
        return new Game(gameFeatures, globalEventBus);
    }

}
