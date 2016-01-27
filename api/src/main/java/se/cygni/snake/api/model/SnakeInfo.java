package se.cygni.snake.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SnakeInfo {

    final String name;
    final int length;
    final int id;
    final int x;
    final int y;

    @JsonCreator
    public SnakeInfo(
            @JsonProperty("name") String name,
            @JsonProperty("length") int length,
            @JsonProperty("id")int id,
            @JsonProperty("x")int x,
            @JsonProperty("y")int y
    )
    {

        this.name = name;
        this.length = length;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
