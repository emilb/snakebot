package se.cygni.snake.game;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import se.cygni.game.Player;
import se.cygni.snake.api.exception.InvalidPlayerName;
import se.cygni.snake.api.request.RegisterMove;
import se.cygni.snake.api.request.RegisterPlayer;
import se.cygni.snake.api.response.PlayerRegistered;
import se.cygni.snake.api.util.MessageUtils;
import se.cygni.snake.player.IPlayer;
import se.cygni.snake.player.RemotePlayer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Game {

    private final EventBus incomingEventBus;
    private final EventBus outgoingEventBus;
    private final String gameId;
    private Set<IPlayer> players = Collections.synchronizedSet(new HashSet<>());

    public Game() {
        gameId = UUID.randomUUID().toString();
        incomingEventBus = new EventBus("game-" + gameId + "-incoming");
        incomingEventBus.register(this);

        outgoingEventBus = new EventBus("game-" + gameId + "-outgoing");
    }

    @Subscribe
    public void registerPlayer(RegisterPlayer registerPlayer) {
        Player player = new Player(registerPlayer.getPlayerName(), registerPlayer.getColor());
        player.setPlayerId(registerPlayer.getPlayerId());

        if (players.contains(player)) {
            InvalidPlayerName playerNameTaken = new InvalidPlayerName(InvalidPlayerName.PlayerNameInvalidReason.Taken);
            MessageUtils.copyCommonAttributes(registerPlayer, playerNameTaken);
            outgoingEventBus.post(playerNameTaken);
        }

        RemotePlayer remotePlayer = new RemotePlayer(player, this, outgoingEventBus);
        addPlayer(remotePlayer);

        PlayerRegistered playerRegistered = MessageUtils.copyCommonAttributes(registerPlayer, new PlayerRegistered());
        playerRegistered.setGameId(gameId);

        outgoingEventBus.post(playerRegistered);

    }

    @Subscribe
    public void registerMove(RegisterMove registerMove) {

    }

    public void addPlayer(IPlayer player) {
        players.add(player);
    }

    public EventBus getOutgoingEventBus() {
        return outgoingEventBus;
    }

    public EventBus getIncomingEventBus() {
        return incomingEventBus;
    }

    public String getGameId() {
        return gameId;
    }
}
