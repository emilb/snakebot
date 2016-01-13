package se.cygni.snake.game;


import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GameManager {

    public Game createTrainingGame() {
        return new Game();
    }

}
