package se.cygni.snake.api.event;

import se.cygni.snake.api.GameMessage;

public class TestUtil {

    public static void populateBaseData(GameMessage message, String receivingPlayerId, String correlationId, String messageId) {
        message.setRecievingPlayerId(receivingPlayerId);
        message.setCorrelationId(correlationId);
        message.setMessageId(messageId);
    }
}
