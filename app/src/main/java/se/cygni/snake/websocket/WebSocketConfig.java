package se.cygni.snake.websocket;

import com.google.common.eventbus.EventBus;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import se.cygni.snake.websocket.echo.EchoWebSocketHandler;
import se.cygni.snake.websocket.event.EventSocketHandler;

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(echoWebSocketHandler(), "/echo").withSockJS();
        registry.addHandler(eventWebSocketHandler(), "/events").withSockJS();
//        registry.addHandler(snakeWebSocketHandler(), "/snake").withSockJS();
    }

    @Bean
    public WebSocketHandler echoWebSocketHandler() {
        return new EchoWebSocketHandler();
    }

    @Bean
    public WebSocketHandler eventWebSocketHandler() {
        return new EventSocketHandler();
    }

    @Bean
    public EventBus globalEventBus() {
        return new EventBus("globalEventBus");
    }
//    @Bean
//    public WebSocketHandler snakeWebSocketHandler() {
//        return new PerConnectionWebSocketHandler(SnakeWebSocketHandler.class);
//    }
}
