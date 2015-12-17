package se.cygni.game.transformation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import se.cygni.game.Tile;
import se.cygni.game.World;
import se.cygni.game.worldobject.Food;

import java.util.Random;

/**
 * Adds a Food object at random free Tile
 */
public class AddRandomFood implements WorldTransformation {

    private RandomGenerator randomGenerator = RandomGeneratorFactory.createRandomGenerator(new Random());

    // @// TODO: 16/12/15 make this a configurable value
    private static final int MAX_TRY_COUNT = 500;

    @Override
    public World transform(World currentWorld) {

        Tile[][] newWorld = null;
        boolean foundFreeSpot = false;
        int tryCount = 0;

        while (!foundFreeSpot && tryCount < MAX_TRY_COUNT) {
            int w_rand = randomGenerator.nextInt(currentWorld.getWidth());
            int h_rand = randomGenerator.nextInt(currentWorld.getHeight());

            if (currentWorld.isTileEmpty(w_rand, h_rand)) {
                newWorld = ArrayUtils.clone(currentWorld.getWorldmatrix());

                newWorld[w_rand][h_rand] = new Tile(new Food());

                foundFreeSpot = true;
            }
        }
        return new World(newWorld);
    }
}
