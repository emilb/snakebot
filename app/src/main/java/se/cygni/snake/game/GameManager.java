package se.cygni.snake.game;


import org.springframework.stereotype.Component;

@Component
public class GameManager {

    public Game createTrainingGame() {
        return new Game();
    }

}
