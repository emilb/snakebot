package se.cygni.snake.player;

import com.google.common.eventbus.EventBus;
import se.cygni.game.Player;
import se.cygni.game.WorldState;
import se.cygni.snake.api.event.GameEndedEvent;
import se.cygni.snake.api.event.GameStartingEvent;
import se.cygni.snake.api.event.MapUpdateEvent;
import se.cygni.snake.api.event.SnakeDeadEvent;
import se.cygni.snake.api.model.DeathReason;
import se.cygni.snake.api.util.MessageUtils;
import se.cygni.snake.apiconversion.WorldStateConverter;

public class RemotePlayer implements IPlayer {

    private Player player;
    private EventBus outgoingEventBus;
    private boolean alive = true;

    public RemotePlayer(Player player, EventBus outgoingEventBus) {
        this.player = player;
        this.outgoingEventBus = outgoingEventBus;
    }

    @Override
    public void onWorldUpdate(WorldState worldState, String gameId, long gameTick) {

        MapUpdateEvent mue = new MapUpdateEvent(
                gameTick,
                gameId,
                WorldStateConverter.convertWorldState(worldState, gameTick));
        MessageUtils.populateMessageIds(mue);
        mue.setRecievingPlayerId(player.getPlayerId());

        outgoingEventBus.post(mue);
    }

    @Override
    public void onPlayerDied(DeathReason reason, String playerId, int x, int y, String gameId, long gameTick) {

        SnakeDeadEvent sde = new SnakeDeadEvent(reason, playerId, x, y, gameId, gameTick);
        MessageUtils.populateMessageIds(sde);
        sde.setRecievingPlayerId(player.getPlayerId());

        outgoingEventBus.post(sde);
    }

    @Override
    public void onGameWon(String playerWinnerId, String gameId, long gameTick, WorldState worldState) {

        GameEndedEvent gee = new GameEndedEvent(
                playerWinnerId, gameId, gameTick,
                WorldStateConverter.convertWorldState(worldState, gameTick)
        );
        MessageUtils.populateMessageIds(gee);
        gee.setRecievingPlayerId(player.getPlayerId());

        outgoingEventBus.post(gee);
    }

    @Override
    public void onGameStart(String gameId, int noofPlayers, int width, int height) {

        GameStartingEvent gse = new GameStartingEvent(gameId, noofPlayers, width, height);
        MessageUtils.populateMessageIds(gse);
        gse.setRecievingPlayerId(player.getPlayerId());

        outgoingEventBus.post(gse);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void dead() {
        alive = false;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getColor() {
        return player.getColor();
    }

    @Override
    public String getPlayerId() {
        return player.getPlayerId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemotePlayer that = (RemotePlayer) o;

        return player != null ? player.equals(that.player) : that.player == null;

    }

    @Override
    public int hashCode() {
        return player != null ? player.hashCode() : 0;
    }
}
