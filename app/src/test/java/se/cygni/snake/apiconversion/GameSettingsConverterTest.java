package se.cygni.snake.apiconversion;

import org.junit.Test;
import se.cygni.snake.api.model.GameSettings;
import se.cygni.snake.game.GameFeatures;

import static org.junit.Assert.assertEquals;

public class GameSettingsConverterTest {

    @Test
    public void testToGameSettings() throws Exception {

    }

    @Test
    public void testToGameFeatures() throws Exception {
        GameSettings gs = new GameSettings.GameSettingsBuilder()
                .withWidth(33)
                .withHeight(43)
                .withMaxNoofPlayers(3)
                .build();

        GameFeatures gf = GameSettingsConverter.toGameFeatures(gs);

        assertEquals(33, gf.width);
        assertEquals(43, gf.height);
        assertEquals(3, gf.maxNoofPlayers);
    }
}