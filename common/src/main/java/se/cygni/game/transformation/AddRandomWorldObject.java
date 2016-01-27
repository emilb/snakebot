package se.cygni.game.transformation;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import se.cygni.game.Tile;
import se.cygni.game.WorldState;
import se.cygni.game.worldobject.WorldObject;

import java.util.Random;

/**
 * Adds a WorldObject at random free Tile
 */
public class AddRandomWorldObject implements WorldTransformation {

    private final WorldObject worldObject;
    private RandomGenerator randomGenerator = RandomGeneratorFactory.createRandomGenerator(new Random());

    public AddRandomWorldObject(WorldObject worldObject) {
        this.worldObject = worldObject;
    }

    @Override
    public WorldState transform(WorldState currentWorld) {

        int[] emptyPositions = currentWorld.listEmptyPositions();
        if (emptyPositions.length == 0) {
            return currentWorld;
        }

        int randomPosition = emptyPositions[randomGenerator.nextInt(emptyPositions.length)];

        Tile[] tiles = currentWorld.getTiles();
        tiles[randomPosition] = new Tile(worldObject);

        return new WorldState(currentWorld.getWidth(), currentWorld.getHeight(), tiles);
    }
}
