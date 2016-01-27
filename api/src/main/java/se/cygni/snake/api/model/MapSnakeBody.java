package se.cygni.snake.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapSnakeBody implements TileContent {

    public static final String CONTENT = "snakebody";

    final boolean tail;
    final int id;
    final int order;

    @JsonCreator
    public MapSnakeBody(
            @JsonProperty("tail") boolean tail,
            @JsonProperty("id") int id,
            @JsonProperty("order") int order)
    {
        this.tail = tail;
        this.id = id;
        this.order = order;
    }

    @Override
    public String getContent() {
        return CONTENT;
    }

    public boolean isTail() {
        return tail;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    @JsonIgnore
    public String toDisplay() {
        return "SB";
    }
}
