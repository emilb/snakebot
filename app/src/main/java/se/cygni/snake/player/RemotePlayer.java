package se.cygni.snake.player;

import com.google.common.eventbus.EventBus;
import se.cygni.game.Player;
import se.cygni.game.WorldState;
import se.cygni.snake.api.event.WorldUpdateEvent;
import se.cygni.snake.api.util.MessageUtils;
import se.cygni.snake.game.Game;

public class RemotePlayer implements IPlayer {

    private Player player;
    private Game game;
    private EventBus outgoingEventBus;

    public RemotePlayer(Player player, Game game, EventBus outgoingEventBus) {
        this.player = player;
        this.game = game;
        this.outgoingEventBus = outgoingEventBus;
    }

    @Override
    public void respondWithNextMove(WorldState world) {
        WorldUpdateEvent wue = new WorldUpdateEvent();

        MessageUtils.populateMessageIds(wue);
        wue.setPlayerId(player.getPlayerId());

        outgoingEventBus.post(wue);
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
    public char getHeadChar() {
        return player.getHeadChar();
    }

    @Override
    public char getTailChar() {
        return player.getTailChar();
    }

    @Override
    public String getPlayerId() {
        return player.getPlayerId();
    }
}
