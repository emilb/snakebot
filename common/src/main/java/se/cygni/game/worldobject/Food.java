package se.cygni.game.worldobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Creat
 *
 * ed by emil on 16/12/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Food implements WorldObject {
    @Override
    public String toString() {
        return "Food{}";
    }
}
