package se.cygni.game.worldobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by emil on 16/12/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Empty implements WorldObject {
    @Override
    public String toString() {
        return "Empty{}";
    }
}
