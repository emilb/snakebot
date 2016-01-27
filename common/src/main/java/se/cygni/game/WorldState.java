package se.cygni.game;

import org.apache.commons.lang3.ArrayUtils;
import se.cygni.game.enums.Direction;
import se.cygni.game.worldobject.Empty;
import se.cygni.game.worldobject.Food;
import se.cygni.game.worldobject.Obstacle;
import se.cygni.game.worldobject.WorldObject;

import java.util.stream.IntStream;

public class WorldState {

    private int width, height;
    private Tile[] tiles;

    public WorldState(int width, int height) {
        this.width = width;
        this.height = height;
        initEmptyWorld();
    }

    public WorldState(int width, int height, Tile[] tiles) {
        this.width = width;
        this.height = height;
        this.tiles = ArrayUtils.clone(tiles);
    }

    public WorldState(WorldState worldState) {
        this.width = worldState.getWidth();
        this.height = worldState.getHeight();
        this.tiles = ArrayUtils.clone(worldState.getTiles());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Coordinate translatePosition(int position) {
        int y = position / width;
        int x = position - y * width;
        return new Coordinate(x, y);
    }

    public int translateCoordinate(Coordinate coordinate) {
        return coordinate.getX() + coordinate.getY() * width;
    }

    public int getSize() {
        return width * height;
    }

    public Tile getTile(Coordinate coordinate) {
        return getTile(translateCoordinate(coordinate));
    }

    public Tile getTile(int position) {
        if (position < 0 || position > getSize())
            throw new RuntimeException("Out of bounds on world matrix");

        return tiles[position];
    }

    public boolean isTileEmpty(Coordinate coordinate) {
        return isTileEmpty(translateCoordinate(coordinate));
    }

    public boolean isTileEmpty(int position) {
        return isTileContentOfType(position, Empty.class);
    }

    public <T extends WorldObject> boolean isTileContentOfType(Coordinate coordinate, Class<T> clazz) {
        return isTileContentOfType(translateCoordinate(coordinate), clazz);
    }

    public <T extends WorldObject> boolean isTileContentOfType(int position, Class<T> clazz) {
        return getTile(position).getContent().getClass() == clazz;
    }

    public Coordinate getPositionForAdjacent(Coordinate coordinate, Direction direction) {
        return translatePosition(
                getPositionForAdjacent(
                        translateCoordinate(coordinate), direction));
    }

    public int getPositionForAdjacent(int position, Direction direction) {
        int size = getSize();

        int newPosition;

        switch (direction) {
            case DOWN:  newPosition = position + width; break;
            case UP:    newPosition = position - width; break;
            case RIGHT: newPosition = position + 1; break;
            case LEFT:  newPosition = position - 1; break;
            default:    newPosition = -1; break;
        }

        // Out of hard bounds (top of bottom)
        if (newPosition < 0 || newPosition >= size)
            throw new RuntimeException("Out of bounds on world matrix");

        // Hit a wall (left or right)
        switch (direction) {
            case DOWN:  break;
            case UP:    break;
            case RIGHT:
                if (newPosition % width == 0)
                    throw new RuntimeException("Out of bounds on world matrix");
                break;
            case LEFT:
                if (position % width == 0)
                    throw new RuntimeException("Out of bounds on world matrix");
                break;
            default:    break;
        }

        return newPosition;
    }

    public int[] listPositionsWithContentOf(Class clazz) {
        return IntStream.range(0, getSize()).filter( position ->
            isTileContentOfType(position, clazz)).toArray();
    }

    public int[] listEmptyPositions() {
        return listPositionsWithContentOf(Empty.class);
    }

    public int[] listFoodPositions() {
        return listPositionsWithContentOf(Food.class);
    }

    public int[] listObstaclePositions() {
        return listPositionsWithContentOf(Obstacle.class);
    }

    /**
     * @return a copy of the tiles
     */
    public Tile[] getTiles() {
        return ArrayUtils.clone(tiles);
    }

    /**
     * The world is represented by a single array
     */
    private void initEmptyWorld() {
        int size = getSize();
        tiles = new Tile[size];

        IntStream.range(0, size).forEach(
                pos -> {
                    tiles[pos] = new Tile();
                }
        );
    }
}
