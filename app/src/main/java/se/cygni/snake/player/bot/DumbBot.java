package se.cygni.snake.player.bot;

import se.cygni.game.WorldState;
import se.cygni.snake.player.IPlayer;

public class DumbBot implements IPlayer {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public char getHeadChar() {
        return 0;
    }

    @Override
    public char getTailChar() {
        return 0;
    }

    @Override
    public String getPlayerId() {
        return null;
    }

    @Override
    public void respondWithNextMove(WorldState world) {
    }
}
