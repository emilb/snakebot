package se.cygni.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import se.cygni.game.serializers.TileSerializer;
import se.cygni.game.worldobject.Empty;
import se.cygni.game.worldobject.WorldObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = TileSerializer.class)
public class Tile {

    private final WorldObject content;

    public Tile () {
        content = new Empty();
    }

    public Tile(WorldObject content) {
        this.content = content;
    }

    public Tile(Tile copy) {
        this(copy.getContent());
    }

    public WorldObject getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "content=" + content +
                '}';
    }
}
