package se.cygni.game.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import se.cygni.game.Tile;

import java.io.IOException;

public class TileSerializer extends JsonSerializer<Tile> {
    @Override
    public void serialize(Tile value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("content", value.getContent().toString());
        jgen.writeEndObject();
    }
}



