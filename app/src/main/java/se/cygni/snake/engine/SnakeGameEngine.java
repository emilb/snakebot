package se.cygni.snake.engine;

import org.springframework.stereotype.Component;
import se.cygni.game.Tile;
import se.cygni.game.World;
import se.cygni.game.enums.Direction;
import se.cygni.game.transformation.AddRandomFood;
import se.cygni.game.worldobject.Obstacle;
import se.cygni.game.worldobject.SnakeHead;

import java.util.stream.IntStream;

@Component
public class SnakeGameEngine {
    private World world;
    private boolean started = false;

    public World createNewGame(int height, int width) {
        Tile[][] worldMatrix = createWorldMatrix(height, width);
        world = new World(worldMatrix);
        return world;
    }

    public boolean startGame() {
        started = true;
        return true;
    }

    private void addStartingPosition(SnakeHead snake) {
        world.getWorldmatrix()[1][3] = new Tile(snake);
    }

    public World movePlayer(String id, Direction direction) {
        SnakeHead snakeHead = world.getPlayers().get(id);
        int x = snakeHead.getPosition().getX();
        int y = snakeHead.getPosition().getY();

        switch (direction) {
            case LEFT:
                moveLeft(snakeHead, x, y);
                break;
            case RIGHT:
                moveRight(snakeHead, x, y);
                break;
            case UP:
                moveUp(snakeHead, x, y);
                break;
            case DOWN:
                moveDown(snakeHead, x, y);
                break;
        }

        return world;
    }

    public String addPlayer(String name) {
        SnakeHead snake = new SnakeHead(name, 1, 3);
        addStartingPosition(snake);
        world.getPlayers().put(snake.getId(), snake);
        return snake.getId();
    }

    public World tick () throws Exception {
        if(started) {
            checkPlayerDead();
            new AddRandomFood().transform(world);
            return world;
        }
        throw new Exception();
    }

    private void checkPlayerDead() {
        world.getPlayers().values().stream().forEach(p ->
        {
            if(world.hasObstacle(p.getPosition())) {
                p.setAlive(false);
            }
        });
    }


    private Tile[][] createWorldMatrix(int height, int length) {
        Tile[][] tiles = new Tile[height][length];

        IntStream.range(0, tiles.length).forEach(
                x -> IntStream.range(0, tiles[x].length).forEach(y -> {
                    if ((x == 0 || x == (tiles.length -1)) || ((y == 0 || y == (tiles[x].length -1)))) {
                        tiles[x][y] = new Tile(new Obstacle());
                    } else {
                        tiles[x][y] = new Tile();
                    }
                }));

        return tiles;
    }

    private void moveRight (SnakeHead snakeHead, int x, int y) {
        world.getWorldmatrix()[x][y] = new Tile();
        world.getWorldmatrix()[x+1][y] = new Tile(snakeHead);
        snakeHead.getPosition().setX(x+1);
    }

    private void moveLeft (SnakeHead snakeHead, int x, int y) {
        world.getWorldmatrix()[x][y] = new Tile();
        world.getWorldmatrix()[x-1][y] = new Tile(snakeHead);
        snakeHead.getPosition().setX(x-1);
    }

    private void moveUp (SnakeHead snakeHead, int x, int y) {
        world.getWorldmatrix()[x][y] = new Tile();
        world.getWorldmatrix()[x][y-1] = new Tile(snakeHead);
        snakeHead.getPosition().setY(y-1);
    }

    private void moveDown (SnakeHead snakeHead, int x, int y) {
        world.getWorldmatrix()[x][y] = new Tile();
        world.getWorldmatrix()[x][y+1] = new Tile(snakeHead);
        snakeHead.getPosition().setY(y+1);
    }
}