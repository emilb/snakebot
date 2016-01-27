package se.cygni.snake.player;

import se.cygni.game.WorldState;

public interface IPlayer {

    public void respondWithNextMove(WorldState world);

    public String getName();

    public String getColor();

    public char getHeadChar();

    public char getTailChar();

    public String getPlayerId();
}
