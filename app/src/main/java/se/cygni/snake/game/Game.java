package se.cygni.snake.game;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import se.cygni.game.Player;
import se.cygni.game.enums.Direction;
import se.cygni.snake.api.exception.InvalidPlayerName;
import se.cygni.snake.api.model.GameSettings;
import se.cygni.snake.api.request.RegisterMove;
import se.cygni.snake.api.request.RegisterPlayerTraining;
import se.cygni.snake.api.response.PlayerRegistered;
import se.cygni.snake.api.util.MessageUtils;
import se.cygni.snake.apiconversion.DirectionConverter;
import se.cygni.snake.apiconversion.GameSettingsConverter;
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
    private final GameFeatures gameFeatures;
    private final GameEngine gameEngine;
    private final EventBus globalEventBus;

    public Game(GameFeatures gameFeatures, EventBus globalEventBus) {

        this.globalEventBus = globalEventBus;
        this.gameFeatures = gameFeatures;
        gameEngine = new GameEngine(gameFeatures, this);
        gameId = UUID.randomUUID().toString();
        incomingEventBus = new EventBus("game-" + gameId + "-incoming");
        incomingEventBus.register(this);

        outgoingEventBus = new EventBus("game-" + gameId + "-outgoing");
    }

    @Subscribe
    public void registerPlayer(RegisterPlayerTraining registerPlayerTraining) {
        Player player = new Player(registerPlayerTraining.getPlayerName(), registerPlayerTraining.getColor());
        player.setPlayerId(registerPlayerTraining.getRecievingPlayerId());

        if (players.contains(player)) {
            InvalidPlayerName playerNameTaken = new InvalidPlayerName(InvalidPlayerName.PlayerNameInvalidReason.Taken);
            MessageUtils.copyCommonAttributes(registerPlayerTraining, playerNameTaken);
            outgoingEventBus.post(playerNameTaken);
            return;
        }

        RemotePlayer remotePlayer = new RemotePlayer(player, outgoingEventBus);
        addPlayer(remotePlayer);

        GameSettings gameSettings = GameSettingsConverter.toGameSettings(gameFeatures);
        PlayerRegistered playerRegistered = new PlayerRegistered(gameId, player.getName(), player.getColor(), gameSettings);
        MessageUtils.copyCommonAttributes(registerPlayerTraining, playerRegistered);

        outgoingEventBus.post(playerRegistered);
    }

    @Subscribe
    public void registerMove(RegisterMove registerMove) {
        long gameTick = registerMove.getGameTick();
        String playerId = registerMove.getRecievingPlayerId();
        Direction direction = DirectionConverter.toDirection(registerMove.getDirection());
        gameEngine.registerMove(
                gameTick,
                playerId,
                direction
        );
    }

    public void startGame() {
        gameEngine.startGame();
    }

    public void addPlayer(IPlayer player) {
        players.add(player);
    }

    public Set<IPlayer> getPlayers() {
        return players;
    }

    public int getNoofPlayers() {
        return players.size();
    }

    public IPlayer getPlayer(String playerId) {
        return players.stream().filter(player -> player.getPlayerId().equals(playerId)).findFirst().get();
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

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public Set<IPlayer> getLivePlayers() {
        // ToDo: filter out dead players
        return getPlayers();
    }

    public void playerLostConnection(String playerId) {
        getPlayer(playerId).dead();
    }

    public EventBus getGlobalEventBus() {
        return globalEventBus;
    }
}
