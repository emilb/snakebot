package se.cygni.snake.api.util;

import org.springframework.beans.BeanUtils;
import se.cygni.snake.api.GameMessage;

import java.util.UUID;

public class MessageUtils {

    public static <T extends GameMessage> T copyCommonAttributes(GameMessage src, T dst) {
        BeanUtils.copyProperties(src, dst);
        return dst;
    }

    public static void populateMessageIds(GameMessage gameMessage) {
        gameMessage.setCorrelationId(UUID.randomUUID().toString());
        gameMessage.setMessageId(UUID.randomUUID().toString());
    }
}
