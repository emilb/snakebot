package se.cygni.snake.websocket.event;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import se.cygni.game.WorldState;
import se.cygni.game.testutil.SnakeTestUtil;
import se.cygni.game.transformation.AddWorldObjectAtRandomPosition;
import se.cygni.game.worldobject.Food;
import se.cygni.game.worldobject.Obstacle;
import se.cygni.game.worldobject.SnakeBody;
import se.cygni.game.worldobject.SnakeHead;
import se.cygni.snake.api.GameMessageParser;
import se.cygni.snake.api.event.MapUpdateEvent;
import se.cygni.snake.api.model.Map;
import se.cygni.snake.apiconversion.WorldStateConverter;

public class EventSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(EventSocketHandler.class);

    @Autowired
    EventBus globalEventBus;

    private WebSocketSession session;

    private boolean isConnected = false;

    public EventSocketHandler() {
        //globalEventBus.register(this);
        logger.info("EventSocketHandler started!");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Opened new session in instance " + this);
        this.session = session;
        isConnected = true;
        // Lambda Runnable
        Runnable eventGenerator = () -> {
            while (isConnected) {
                long worldTick = 0;
                try {
                    session.sendMessage(new TextMessage(GameMessageParser.encodeMessage(getRandomMapUpdateEvent(worldTick))));
                    worldTick++;
                    Thread.sleep(250);
                    logger.info("World tick: {}", worldTick);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(eventGenerator).start();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        logger.debug(message.getPayload());
        session.sendMessage(new TextMessage(message.getPayload()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        isConnected = false;
    }

    @Subscribe
    public void onMapUpdate(MapUpdateEvent event) {
//        try {
//            session.sendMessage(new TextMessage(GameMessageParser.encodeMessage(event)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }



    private MapUpdateEvent getRandomMapUpdateEvent(long gameTick) {
        return new MapUpdateEvent(gameTick, "666", getRandomMap());
    }

    private Map getRandomMap() {
        WorldState ws = new WorldState(15, 15);

        // Snake Python
        String snakeName = "python";
        SnakeHead head = new SnakeHead(snakeName, "id_python", 101);
        SnakeBody body1 = new SnakeBody(116);
        SnakeBody body2 = new SnakeBody(115);

        head.setNextSnakePart(body1);
        body1.setNextSnakePart(body2);

        ws = SnakeTestUtil.replaceWorldObjectAt(ws, head, head.getPosition());
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, body1, body1.getPosition());
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, body2, body2.getPosition());

        // Snake Cobra
        String snakeName2 = "cobra";
        SnakeHead head2 = new SnakeHead(snakeName2, "id_cobra", 109);
        SnakeBody body21 = new SnakeBody(108);
        SnakeBody body22 = new SnakeBody(123);
        SnakeBody body23 = new SnakeBody(138);

        head2.setNextSnakePart(body21);
        body21.setNextSnakePart(body22);
        body22.setNextSnakePart(body23);

        ws = SnakeTestUtil.replaceWorldObjectAt(ws, head2, head2.getPosition());
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, body21, body21.getPosition());
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, body22, body22.getPosition());
        ws = SnakeTestUtil.replaceWorldObjectAt(ws, body23, body23.getPosition());

        // 10 Obstacles
        for (int x=0; x<10; x++) {
            AddWorldObjectAtRandomPosition ar = new AddWorldObjectAtRandomPosition(new Obstacle());
            ws = ar.transform(ws);
        }

        // 5 Foods
        for (int x=0; x<10; x++) {
            AddWorldObjectAtRandomPosition ar = new AddWorldObjectAtRandomPosition(new Food());
            ws = ar.transform(ws);
        }

        return WorldStateConverter.convertWorldState(ws, 1);
    }
}
