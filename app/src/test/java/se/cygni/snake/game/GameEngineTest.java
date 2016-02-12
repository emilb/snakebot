package se.cygni.snake.game;

import org.junit.Before;
import org.junit.Test;
import se.cygni.game.WorldState;
import se.cygni.game.worldobject.SnakeHead;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

    private GameEngine gameEngine;
    private Game game;

    @Before
    public void setup() {
        GameFeatures gameFeatures = new GameFeatures();
        gameFeatures.width = 100;
        gameFeatures.height = 100;
        gameFeatures.maxNoofPlayers = 2;

        GameManager gameManager = new GameManager();
        game = gameManager.createTrainingGame();
        gameEngine = game.getGameEngine();
    }

//    @Test
//    public void testJoinGame() {
//        TestPlayer tp = new TestPlayer("junit", "junit_id", game);
//        game.addPlayer(tp);
//
//        game.startGame();
//
//        WorldState ws = gameEngine.getWorld();
//        assertEquals(1, ws.listPositionsWithContentOf(SnakeHead.class).length);
//    }

}
