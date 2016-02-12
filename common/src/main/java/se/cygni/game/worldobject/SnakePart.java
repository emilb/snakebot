package se.cygni.game.worldobject;

public interface SnakePart extends WorldObject {

    SnakePart getNextSnakePart();
    void setNextSnakePart(SnakePart nextSnakePart);
    boolean isHead();
    boolean isTail();
    int getPosition();
    void setPosition(int position);
}
