package se.cygni.game.worldobject;


import se.cygni.game.Position;

import java.util.UUID;

public class SnakeHead implements WorldObject {
    private final String name;
    private final String id;
    private Position position;
    private boolean alive;


    public SnakeHead(String name, int x, int y) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.position = new Position(x,y);
        this.alive = true;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
