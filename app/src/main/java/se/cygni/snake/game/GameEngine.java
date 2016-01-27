package se.cygni.snake.game;

import se.cygni.game.WorldState;
import se.cygni.snake.player.IPlayer;

/**
 * GameEngine is responsible for:
 *
 * - Maintaining the world
 * - Handling the time ticker
 * - Executing player moves
 * -
 */
public class GameEngine {

    private final GameFeatures gameFeatures;
    private final Game game;
    private WorldState world;
    private long currentWorldTick = 0;

    public GameEngine(GameFeatures gameFeatures, Game game) {
        this.gameFeatures = gameFeatures;
        this.game = game;
    }

    public void addPlayer(IPlayer player) {

    }

    public void startGame() {

    }

    private void initGame() {
        world = new WorldState(gameFeatures.width, gameFeatures.height);

        // Place players
    }

    private void gameLoop() {

    }


}
