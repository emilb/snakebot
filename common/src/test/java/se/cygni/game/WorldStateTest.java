package se.cygni.game;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import se.cygni.game.enums.Direction;
import se.cygni.game.testutil.SnakeTestUtil;
import se.cygni.game.worldobject.Food;
import se.cygni.game.worldobject.Obstacle;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class WorldStateTest {

    @Test
    public void testGetSize() throws Exception {
        WorldState ws = new WorldState(10, 10);
        assertEquals(100, ws.getSize());
    }

    @Test
    public void testGetTile() throws Exception {
        WorldState ws = new WorldState(10, 10);
        Tile[] tiles = ws.getTiles();

        // Place a food on tile 12 => coordinate 2,1
        Food food = new Food();
        tiles[12] = new Tile(food);

        ws = new WorldState(10, 10, tiles);

        assertEquals(food, ws.getTile(12).getContent());
        assertEquals(food, ws.getTile(new Coordinate(2,1)).getContent());
    }

    @Test
    public void testTranslateCoordinate() throws Exception {
        WorldState ws = new WorldState(10, 10);

        assertEquals("Wrong translation to position",
                15, ws.translateCoordinate(new Coordinate(5,1)));

        assertEquals("Wrong translation to position",
                99, ws.translateCoordinate(new Coordinate(9,9)));
    }

    @Test
    public void testTranslatePosition() throws Exception {
        WorldState ws = new WorldState(6, 3);

        Coordinate expectedCoordinate = new Coordinate(2, 1);
        Coordinate coordinate = ws.translatePosition(8);

        assertEquals("Y coordinate wrong", expectedCoordinate.getY(), coordinate.getY());
        assertEquals("X coordinate wrong", expectedCoordinate.getX(), coordinate.getX());
    }

    @Test
    public void testTranslatePositionLast() throws Exception {
        WorldState ws = new WorldState(10, 10);

        Coordinate expectedCoordinate = new Coordinate(9, 9);
        Coordinate coordinate = ws.translatePosition(99);

        assertEquals("Y coordinate wrong", expectedCoordinate.getY(), coordinate.getY());
        assertEquals("X coordinate wrong", expectedCoordinate.getX(), coordinate.getX());
    }

    @Test
    public void testIsTileEmpty() throws Exception {
        // Place a food on tile 12 => coordinate 2,1
        WorldState ws = SnakeTestUtil.createWorld(Food.class, 10, 10, 12);

        assertFalse(ws.isTileEmpty(12));
        assertFalse(ws.isTileEmpty(new Coordinate(2,1)));
    }

    @Test
    public void testGetPositionForAdjacent() throws Exception {
        WorldState ws = new WorldState(10, 10);

        // Normal adjacents

        // 0,0 DOWN
        assertEquals(10, ws.getPositionForAdjacent(0, Direction.DOWN));

        // 0,0 RIGHT
        assertEquals(1, ws.getPositionForAdjacent(0, Direction.RIGHT));

        // 10,10 UP
        assertEquals(89, ws.getPositionForAdjacent(99, Direction.UP));

        // 10,10 LEFT
        assertEquals(98, ws.getPositionForAdjacent(99, Direction.LEFT));

        // 0,1 UP
        assertEquals(0, ws.getPositionForAdjacent(10, Direction.UP));

        // Corner adjacents out of bounds

        try {
            // Should fail
            assertEquals(-10, ws.getPositionForAdjacent(0, Direction.UP));
            fail("Allowed adjacent tile out of bounds, UP from 0,0");
        } catch (RuntimeException rte) {}

        try {
            // Should fail
            assertEquals(-1, ws.getPositionForAdjacent(0, Direction.LEFT));
            fail("Allowed adjacent tile out of bounds, LEFT from 0,0");
        } catch (RuntimeException rte) {}

        try {
            // Should fail
            assertEquals(100, ws.getPositionForAdjacent(99, Direction.RIGHT));
            fail("Allowed adjacent tile out of bounds, RIGHT from 9,9");
        } catch (RuntimeException rte) {}

        try {
            // Should fail
            assertEquals(109, ws.getPositionForAdjacent(99, Direction.DOWN));
            fail("Allowed adjacent tile out of bounds, DOWN from 9,9");
        } catch (RuntimeException rte) {}

        // Left and Right edge adjacents out of bounds

        try {
            // Should fail
            assertEquals(20, ws.getPositionForAdjacent(19, Direction.RIGHT));
            fail("Allowed adjacent tile out of bounds, RIGHT from 1,9");
        } catch (RuntimeException rte) {}

        try {
            // Should fail
            assertEquals(9, ws.getPositionForAdjacent(10, Direction.LEFT));
            fail("Allowed adjacent tile out of bounds, LEFT from 0,1");
        } catch (RuntimeException rte) {}

    }

    @Test
    public void testListPositionsWithContentOf() throws Exception {
        WorldState ws = new WorldState(10, 10);

        // Change some tiles
        Tile[] tiles = ws.getTiles();

        tiles[5] = new Tile(new Food());
        tiles[15] = new Tile(new Food());
        tiles[25] = new Tile(new Food());
        tiles[78] = new Tile(new Obstacle());
        tiles[88] = new Tile(new Obstacle());
        tiles[92] = new Tile(new Obstacle());
        tiles[98] = new Tile(new Obstacle());

        WorldState newWorld = new WorldState(ws.getWidth(), ws.getHeight(), tiles);

        int[] foodPositions = newWorld.listPositionsWithContentOf(Food.class);
        int[] obstaclePositions = newWorld.listPositionsWithContentOf(Obstacle.class);

        assertEquals(3, foodPositions.length);
        assertEquals(4, obstaclePositions.length);

        assertArrayEquals(new int[] {5, 15, 25}, foodPositions);
        assertArrayEquals(new int[] {78, 88, 92, 98}, obstaclePositions);
    }

    @Test
    public void testListEmptyPositions_AllEmpty() throws Exception {

        WorldState ws = new WorldState(10, 10);

        int[] emptyPositions = ws.listEmptyPositions();
        assertEquals(100, emptyPositions.length);
        assertArrayEquals(IntStream.range(0, 100).toArray(), emptyPositions);
    }

    @Test
    public void testListEmptyPositions_NotAllEmpty() throws Exception {

        WorldState ws = SnakeTestUtil.createWorld(Food.class, 10, 10, new int[] { 50 });

        int[] emptyPositions = ws.listEmptyPositions();
        assertEquals(99, emptyPositions.length);

        assertArrayEquals(IntStream.range(0, 100).filter( pos -> pos != 50).toArray(), emptyPositions);
    }

    @Test
    public void testListEmptyPositions() throws Exception {

        int[] foodPositions = new int[] { 2, 6, 85 };
        WorldState ws = SnakeTestUtil.createWorld(Food.class, 10, 10, foodPositions);

        int[] emptyPositions = IntStream.range(0, 100).filter( pos->
            !ArrayUtils.contains(foodPositions, pos)
        ).toArray();

        assertArrayEquals(emptyPositions, ws.listEmptyPositions());
    }

    @Test
    public void testListFoodPositions() throws Exception {

        int[] foodPositions = new int[] { 2, 6, 85 };
        WorldState ws = SnakeTestUtil.createWorld(Food.class, 10, 10, foodPositions);

        assertArrayEquals(foodPositions, ws.listFoodPositions());
    }

    @Test
    public void testListObstaclePositions() throws Exception {

        int[] obstaclePositions = new int[] { 8, 13, 23, 55, 87, 99 };
        WorldState ws = SnakeTestUtil.createWorld(Obstacle.class, 10, 10, obstaclePositions);

        assertArrayEquals(obstaclePositions, ws.listObstaclePositions());
    }
}