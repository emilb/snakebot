package se.cygni.snake.player.bot;

import se.cygni.game.WorldState;
import se.cygni.snake.api.model.DeathReason;
import se.cygni.snake.player.IPlayer;

public abstract class BotPlayer implements IPlayer {

    private boolean alive = true;

    @Override
    public void onWorldUpdate(WorldState worldState, String gameId, long gameTick) {

    }

    @Override
    public void onPlayerDied(DeathReason reason, String playerId, int x, int y, String gameId, long gameTick) {

    }

    @Override
    public void onGameWon(String playerWinnerId, String gameId, long gameTick, WorldState worldState) {

    }

    @Override
    public void onGameStart(String gameId, int noofPlayers, int width, int height) {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void dead() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public String getPlayerId() {
        return null;
    }
}
