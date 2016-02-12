package se.cygni.snake.game;

import se.cygni.game.Coordinate;
import se.cygni.game.WorldState;
import se.cygni.game.enums.Direction;
import se.cygni.game.exception.ObstacleCollision;
import se.cygni.game.exception.SnakeCollision;
import se.cygni.game.exception.TransformationException;
import se.cygni.game.exception.WallCollision;
import se.cygni.game.transformation.AddWorldObjectAtRandomPosition;
import se.cygni.game.transformation.MoveSnake;
import se.cygni.game.transformation.RemoveSnake;
import se.cygni.game.worldobject.SnakeHead;
import se.cygni.snake.api.event.MapUpdateEvent;
import se.cygni.snake.api.model.DeathReason;
import se.cygni.snake.apiconversion.WorldStateConverter;
import se.cygni.snake.player.IPlayer;

import java.util.HashMap;

/**
 * GameEngine is responsible for:
 *
 * - Maintaining the world
 * - Handling the time ticker
 * - Executing player moves
 * - Executing the rules from GameFeatures
 */
public class GameEngine {

    private final GameFeatures gameFeatures;
    private final Game game;
    private WorldState world;
    private long currentWorldTick = 0;
    private java.util.Map<String, Direction> snakeDirections;
    private WorldStateConverter converter;

    public GameEngine(GameFeatures gameFeatures, Game game) {
        this.gameFeatures = gameFeatures;
        this.game = game;
        this.converter = new WorldStateConverter();
    }

    public void startGame() {
        initGame();
        gameLoop();
    }

    private void initGame() {
        world = new WorldState(gameFeatures.width, gameFeatures.height);

        // Place players
        game.getPlayers().stream().forEach( player -> {
            SnakeHead snakeHead = new SnakeHead(player.getName(), player.getPlayerId(), 0);
            AddWorldObjectAtRandomPosition randomPosition = new AddWorldObjectAtRandomPosition(snakeHead);
            world = randomPosition.transform(world);
        });

        game.getPlayers().stream().forEach( player -> {
            player.onGameStart(game.getGameId(), game.getNoofPlayers(), world.getWidth(), world.getHeight());
        });
    }

    private void gameLoop() {
        initSnakeDirections();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                // Loop till winner is decided
                while (isGameRunning()) {

                    game.getPlayers().stream().forEach( player -> {
                        player.onWorldUpdate(
                                world, game.getGameId(), currentWorldTick
                        );
                    });

                    // Todo: Fix this better
                    MapUpdateEvent mue = new MapUpdateEvent(
                            currentWorldTick,
                            game.getGameId(),
                            WorldStateConverter.convertWorldState(world, currentWorldTick));

                    game.getGlobalEventBus().post(mue);

                    // todo: change this to wait max time timeInMsPerTick but continue as soon as all clients have responded
                    try {
                        Thread.sleep(gameFeatures.timeInMsPerTick);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    currentWorldTick++;

                    // Make order of snake direction execution more fair (i.e. execute in order of
                    // of incoming responses)
                    int snakeHeadPositions[] = world.listPositionsWithContentOf(SnakeHead.class);

                    for (int pos : snakeHeadPositions) {
                        SnakeHead head = (SnakeHead)world.getTile(pos).getContent();

                        MoveSnake move = new MoveSnake(head, snakeDirections.get(head.getPlayerId()));

                        try {
                            world = move.transform(world);
                        } catch (ObstacleCollision oc) {
                            snakeDied(head, DeathReason.CollisionWithObstacle, oc.getPosition());
                        } catch (WallCollision wc) {
                            snakeDied(head, DeathReason.CollisionWithWall, wc.getPosition());
                        } catch (SnakeCollision sc) {
                            snakeDied(head, DeathReason.CollisionWithSnake, sc.getPosition());
                        } catch (TransformationException oc) {
                            snakeDied(head, DeathReason.CollisionWithObstacle, 0);
                        }
                    }

                    // Add random objects
                    if (gameFeatures.foodEnabled) {
                        randomFood();
                    }

                    if (gameFeatures.obstaclesEnabled) {
                        randomObstacle();
                    }
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
    }

    private void randomObstacle() {
    }


    private void randomFood() {
    }

    private void initSnakeDirections() {
        snakeDirections = new HashMap<>();

        game.getPlayers().stream().forEach( player -> {
            snakeDirections.put(player.getPlayerId(), getRandomDirection());
        });
    }

    private boolean isGameRunning() {
        return game.getLivePlayers().size() > 1;
    }

    public void registerMove(long gameTick, String playerId, Direction direction) {
        IPlayer player = game.getPlayer(playerId);

        if (gameTick == currentWorldTick) {
            snakeDirections.put(playerId, direction);
        } else {

//            if (player != null)
//                player.onToLateRegisterMove(currentWorldTick, gameTick);
        }
    }

    private Direction getRandomDirection() {
        // Todo: random direction
        return Direction.DOWN;
    }

    private void snakeDied(SnakeHead head, DeathReason deathReason, int position) {
        RemoveSnake remove = new RemoveSnake(head);
        try {
            world = remove.transform(world);
        } catch (Exception e) { e.printStackTrace(); }
        game.getPlayer(head.getPlayerId()).dead();
        snakeDirections.remove(head.getPlayerId());

        Coordinate coordinate = world.translatePosition(position);

        game.getPlayers().stream().forEach( player -> {
            player.onPlayerDied(
                    deathReason,
                    head.getPlayerId(),
                    coordinate.getX(), coordinate.getY(),
                    game.getGameId(), currentWorldTick
            );
        });
    }
}
