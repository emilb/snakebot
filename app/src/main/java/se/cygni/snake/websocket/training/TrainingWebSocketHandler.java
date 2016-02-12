package se.cygni.snake.websocket.training;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.GameMessageParser;
import se.cygni.snake.game.Game;
import se.cygni.snake.game.GameManager;

import java.io.IOException;
import java.util.UUID;

public class TrainingWebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(TrainingWebSocketHandler.class);

    @Autowired
    private GameManager gameManager;

    private WebSocketSession webSocketSession;
    private final EventBus outgoingEventBus;
    private final EventBus incomingEventBus;
    private final Game game;
    private final String playerId;

    public TrainingWebSocketHandler() {
        // Create a playerId for this player
        playerId = UUID.randomUUID().toString();

        game = gameManager.createTrainingGame();

        // Get an eventbus and register this handler
        outgoingEventBus = game.getOutgoingEventBus();
        outgoingEventBus.register(this);

        incomingEventBus = game.getIncomingEventBus();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        logger.debug("Opened new session in instance " + this);
        this.webSocketSession = session;

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        logger.debug(message.getPayload());

        // Deserialize message
        GameMessage gameMessage = GameMessageParser.decodeMessage(message.getPayload());

        // Add playerId if missing
        if (StringUtils.isEmpty(gameMessage.getRecievingPlayerId())) {
            gameMessage.setRecievingPlayerId(playerId);
        }

        // Send to game
        incomingEventBus.post(gameMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        outgoingEventBus.unregister(this);
        game.playerLostConnection(playerId);
    }

    @Subscribe
    public void sendSnakeMessage(GameMessage message) throws IOException {

        // Verify that this message is intended to this player (or null if for all players)
        if (!StringUtils.isEmpty(message.getRecievingPlayerId()) && !playerId.equals(message.getRecievingPlayerId())) {
            return;
        }

        // Serialize message and send
        webSocketSession.sendMessage(new TextMessage(GameMessageParser.encodeMessage(message)));
    }
}
