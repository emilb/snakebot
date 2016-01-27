package se.cygni.snake.game;

public class GameFeatures {

    // World width
    public int width = 500;

    // World height
    public int height = 500;

    // Maximum noof players in this game
    public int maxNoofPlayers = 5;

    // The starting length of a snake
    public int startSnakeLength = 1;

    // The time clients have to respond with a new move
    public int timeInMsPerTick = 250;

    // Randomly place obstacles
    public boolean obstaclesEnabled = false;

    // Randomly place food
    public boolean foodEnabled = true;

    // Traveling to the edge does not kill but moves to
    // corresponding edge on other side.
    public boolean edgeWrapsAround = false;

    // If a snake manages to nibble on the tail
    // of another snake it will consume that tail part.
    // I.e. the nibbling snake will grow one and
    // victim will loose one.
    public boolean headToTailConsumes = false;

    // Only valid if headToTailConsumes is active.
    // When tailConsumeGrows is set to true the
    // consuming snake will grow when eating
    // another snake.
    public boolean tailConsumeGrows = false;

    // Likelihood (in percent) that a new food will be
    // added to the world
    public final int addFoodLikelihood = 15;

    // Likelihood (in percent) that a
    // food will be removed from the world
    public final int removeFoodLikelihood = 5;

    // Likelihood (in percent) that a new obstacle will be
    // added to the world
    public final int addObstacleLikelihood = 15;

    // Likelihood (in percent) that an
    // obstacle will be removed from the world
    public final int removeObstacleLikelihood = 15;

}
