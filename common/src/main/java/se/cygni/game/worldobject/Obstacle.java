package se.cygni.game.worldobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Obstacle implements WorldObject {

    @Override
    public String toString() {
        return "Obstacle{}";
    }
}
