package se.cygni.snake.engine;

import org.junit.Test;
import se.cygni.game.World;
import se.cygni.game.enums.Direction;
import se.cygni.game.worldobject.Obstacle;

import static org.junit.Assert.*;

public class SnakeGameEngineTest {
    private SnakeGameEngine engine = new SnakeGameEngine();


    @Test
    public void createNewGameTest() {
        World newGame = engine.createNewGame(10, 10);

        assertEquals(newGame.getHeight(), 10);
        assertEquals(newGame.getWidth(), 10);
        assertEquals(newGame.getWorldmatrix().length, 10);
    }

    @Test
    public void newGameCheckOuterWallTest() {
        World newGame = engine.createNewGame(5, 5);

        assertTrue(newGame.getTile(0, 0).getContent() instanceof Obstacle);
        assertTrue(newGame.getTile(4, 4).getContent() instanceof Obstacle);
        assertTrue(newGame.getTile(0, 4).getContent() instanceof Obstacle);
        assertTrue(newGame.getTile(4, 0).getContent() instanceof Obstacle);
        assertFalse(newGame.getTile(3, 1).getContent() instanceof Obstacle);
    }

    @Test
    public void addPlayerTest() {
        World newGame = engine.createNewGame(5, 5);
        String id = engine.addPlayer("hans");
        assertEquals(newGame.getPlayers().size(), 1);
        assertEquals(newGame.getPlayers().get(id).getPosition().getX(), 1);
        assertEquals(newGame.getPlayers().get(id).getPosition().getY(), 3);
        assertEquals(newGame.getPlayers().get(id).getName(), "hans");
    }

    @Test
    public void startGameTest() {
        engine.createNewGame(5, 5);
        assertTrue(engine.startGame());
    }

    @Test
    public void tickTest() throws Exception {
        engine.createNewGame(5, 5);
        String id = engine.addPlayer("hans");
        engine.startGame();
        engine.movePlayer(id, Direction.RIGHT);

        World returnWorld = engine.tick();

        assertEquals(returnWorld.getPlayers().get(id).getPosition().getX(), 2);
        assertEquals(returnWorld.getPlayers().get(id).getPosition().getY(), 3);
    }


}
