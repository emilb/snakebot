package se.cygni.snake.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapSnakeHead implements TileContent {

    public static final String CONTENT = "snakehead";

    final String name;
    final int id;

    @JsonCreator
    public MapSnakeHead(
            @JsonProperty("name") String name,
            @JsonProperty("id") int id)
    {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getContent() {
        return CONTENT;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @JsonIgnore
    public String toDisplay() {
        return "SH";
    }
}
