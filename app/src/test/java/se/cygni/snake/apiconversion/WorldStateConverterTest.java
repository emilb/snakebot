package se.cygni.snake.apiconversion;

import org.junit.Test;
import se.cygni.game.WorldState;
import se.cygni.game.testutil.SnakeTestUtil;
import se.cygni.game.worldobject.*;
import se.cygni.snake.api.GameMessageParser;
import se.cygni.snake.api.model.*;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class WorldStateConverterTest {

    @Test
    public void testConvertWorldStateWithObstacle() throws Exception {
        testConversionWithType(Obstacle.class);
    }

    @Test
    public void testConvertWorldStateWithFood() throws Exception {
        testConversionWithType(Food.class);
    }

    @Test
    public void testConvertWorldStateWithOneSnake() throws Exception {
        WorldState ws = new WorldState(3, 4);

        String snakeName = "junit";
        SnakeHead head = new SnakeHead(snakeName, 5);
        SnakeBody body1 = new SnakeBody(4);
        SnakeBody body2 = new SnakeBody(3);

        head.setNextSnakePart(body1);
        body1.setNextSnakePart(body2);

        ws = SnakeTestUtil.replaceWorldObjectAt(ws, head, 5);  // 5 => (2,1)
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, body1, 4); // 4 => (1,1)
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, body2, 3); // 3 => (0,1)

        WorldStateConverter converter = new WorldStateConverter();
        Map map = converter.convertWorldState(ws);

        // Make sure serialisation works
        String mapStr = GameMessageParser.encodeMessage(map);

        // Make sure deserialisation works
        Map reparsedMap = (Map) GameMessageParser.decodeMessage(mapStr);
        System.out.println(reparsedMap);

        // Assert snakeinfo
        assertEquals(1, reparsedMap.getSnakeInfos().length);
        SnakeInfo si = reparsedMap.getSnakeInfos()[0];
        assertEquals(3, si.getLength());
        assertEquals("junit", si.getName());
        assertEquals(2, si.getX());
        assertEquals(1, si.getY());

        // Assert snake position
        assertEquals(MapSnakeHead.class, reparsedMap.getTiles()[2][1].getClass());
        assertEquals(MapSnakeBody.class, reparsedMap.getTiles()[1][1].getClass());
        assertEquals(MapSnakeBody.class, reparsedMap.getTiles()[0][1].getClass());

        // Assert snake values
        MapSnakeHead mapSnakeHead = (MapSnakeHead)reparsedMap.getTiles()[2][1];
        assertEquals(snakeName, mapSnakeHead.getName());
        assertEquals(snakeName.hashCode(), mapSnakeHead.getId());

        MapSnakeBody mapSnakeBody1 = (MapSnakeBody)reparsedMap.getTiles()[1][1];
        assertEquals(snakeName.hashCode(), mapSnakeBody1.getId());
        assertEquals(1, mapSnakeBody1.getOrder());
        assertFalse(mapSnakeBody1.isTail());

        MapSnakeBody mapSnakeBody2 = (MapSnakeBody)reparsedMap.getTiles()[0][1];
        assertEquals(snakeName.hashCode(), mapSnakeBody2.getId());
        assertEquals(2, mapSnakeBody2.getOrder());
        assertTrue(mapSnakeBody2.isTail());

    }

    private <T extends WorldObject> void testConversionWithType(Class<T> clazz) throws Exception {
        WorldState ws = new WorldState(3, 4);

        WorldObject worldObject = SnakeTestUtil.createWorldObject(clazz);

        // Obstacle at 1,1
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, worldObject, 4);

        WorldStateConverter converter = new WorldStateConverter();
        Map map = converter.convertWorldState(ws);

        // Make sure serialisation works
        String mapStr = GameMessageParser.encodeMessage(map);

        // Make sure deserialisation works
        Map reparsedMap = (Map) GameMessageParser.decodeMessage(mapStr);

        // Assert values
        assertEquals(3, reparsedMap.getWidth());
        assertEquals(4, reparsedMap.getHeight());

        Class mapType = getCorrespondingMapType(worldObject);

        IntStream.range(0, 3).forEach(x -> {
            IntStream.range(0, 4).forEach(y -> {
                if (x == 1 && y == 1) {
                    assertEquals(mapType, reparsedMap.getTiles()[x][y].getClass());
                } else {
                    assertEquals(MapEmpty.class, reparsedMap.getTiles()[x][y].getClass());
                }
            });
        });
    }

    private Class getCorrespondingMapType(WorldObject obj) {
        if (obj instanceof Obstacle)
            return MapObstacle.class;

        if (obj instanceof Food)
            return MapFood.class;

        if (obj instanceof Empty)
            return MapEmpty.class;

        throw new IllegalArgumentException(obj.getClass() + " is not a known type");
    }
}