package se.cygni.snake.api.exception;

import se.cygni.snake.api.GameMessage;
import se.cygni.snake.api.type.GameMessageType;

@GameMessageType
public class InvalidMove extends GameMessage {

    public enum PlayerNameInvalidReason {
        Taken,
        Empty,
        InvalidCharacter
    }

    private PlayerNameInvalidReason reasonCode;

    public InvalidMove(PlayerNameInvalidReason reasonCode) {
        this.reasonCode = reasonCode;
    }

    public PlayerNameInvalidReason getReasonCode() {
        return reasonCode;
    }
}