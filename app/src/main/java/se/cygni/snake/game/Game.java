package se.cygni.snake.game;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import se.cygni.game.Player;
import se.cygni.snake.api.exception.PlayerNameTaken;
import se.cygni.snake.api.request.RegisterPlayer;
import se.cygni.snake.api.response.PlayerRegistered;
import se.cygni.snake.api.util.MessageUtils;

import java.util.*;

public class Game {

    private final EventBus incomingEventBus;
    private final EventBus outgoingEventBus;
    private final String gameId;
    private Set<Player> players = Collections.synchronizedSet(new HashSet<>());

    public Game() {
        gameId = UUID.randomUUID().toString();
        incomingEventBus = new EventBus("game-" + gameId + "-incoming");
        incomingEventBus.register(this);

        outgoingEventBus = new EventBus("game-" + gameId + "-outgoing");
    }

    @Subscribe
    public void registerPlayer(RegisterPlayer registerPlayer) {
        Player player = new Player(registerPlayer.getPlayerName(), registerPlayer.getColor());

        if (players.contains(player)) {
            PlayerNameTaken playerNameTaken = new PlayerNameTaken(PlayerNameTaken.PlayerNameInvalidReason.Taken);
            MessageUtils.copyCommonAttributes(registerPlayer, playerNameTaken);
            outgoingEventBus.post(playerNameTaken);
        }

        addPlayer(player);

        PlayerRegistered playerRegistered = MessageUtils.copyCommonAttributes(registerPlayer, new PlayerRegistered());
        playerRegistered.setGameId(gameId);

        outgoingEventBus.post(playerRegistered);

    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public EventBus getOutgoingEventBus() {
        return outgoingEventBus;
    }

    public EventBus getIncomingEventBus() {
        return incomingEventBus;
    }
}
