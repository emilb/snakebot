package se.cygni.game.transformation;

import org.junit.Test;
import se.cygni.game.World;
import se.cygni.game.worldobject.Food;

import static org.junit.Assert.*;

/**
 * Created by emil on 16/12/15.
 */
public class AddRandomFoodTest {

    @Test
    public void testRandomFoodTransformSimple() throws Exception {
        World world = new World(3,3);

        AddRandomFood randomFood = new AddRandomFood();
        World newWorld = randomFood.transform(world);

        // Ensure that there is exactly one new Food object in the world
        int foodCount = 0;
        for (int w = 0; w < world.getWidth(); w++) {
            for (int h = 0; h < world.getHeight(); h++) {
                if (newWorld.getTile(w, h).getContent() instanceof Food) {
                    foodCount++;
                }
            }
        }

        assertEquals(1, foodCount);
    }


}