package se.cygni.game;

import se.cygni.game.transformation.WorldTransformation;
import se.cygni.game.worldobject.Empty;

import java.util.List;

/**
 * An immutable representation of the current world state.
 */
public class World {

    // The width and height (in Tiles) of the game world
    private final int width, height;

    private final Tile[][] worldmatrix;

    public World(int width, int height) {
        this.width = width;
        this.height = height;

        worldmatrix = new Tile[width][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                worldmatrix[w][h] = new Tile(new Empty());
            }
        }
    }

    public World(Tile[][] worldmatrix) {
        this.width = worldmatrix.length;
        this.height = worldmatrix[0].length;
        this.worldmatrix = worldmatrix;
    }

    public World(final World copy, List<WorldTransformation> transformations) {
        this.width = copy.width;
        this.height = copy.height;

        World resultingWorld = copy;

        for (WorldTransformation t: transformations) {
            resultingWorld = t.transform(resultingWorld);
        }

        this.worldmatrix = resultingWorld.getWorldmatrix();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getWorldmatrix() {
        return worldmatrix;
    }

    public boolean isTileEmpty(int width, int height) {
        if (width >= this.width || height >= this.height)
            return false;

        return worldmatrix[width][height].getContent() instanceof Empty;
    }

    public Tile getTile(int x, int y) {
        if (x > this.width || y > this.height) {
            throw new RuntimeException("Out of bounds on world matrix");
        }

        return worldmatrix[x][y];
    }
}
