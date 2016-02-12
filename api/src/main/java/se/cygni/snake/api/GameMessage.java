package se.cygni.snake.api;

public class GameMessage {

    private String recievingPlayerId;
    private String messageId;
    private String correlationId;
    private final String type = this.getClass().getCanonicalName();

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getType() {
        return type;
    }

    public String getRecievingPlayerId() {
        return recievingPlayerId;
    }

    public void setRecievingPlayerId(String recievingPlayerId) {
        this.recievingPlayerId = recievingPlayerId;
    }
}
