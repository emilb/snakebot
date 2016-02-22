package se.cygni.snake.websocket;

import com.google.common.eventbus.EventBus;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import se.cygni.snake.game.GameManager;
import se.cygni.snake.websocket.echo.EchoWebSocketHandler;
import se.cygni.snake.websocket.event.EventSocketHandler;
import se.cygni.snake.websocket.training.TrainingWebSocketHandler;

//import org.eclipse.jetty.websocket.common.extensions.compress.PerMessageDeflateExtension;
//import org.eclipse.jetty.websocket.server.WebSocketServerFactory;

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(echoWebSocketHandler(), "/echo").withSockJS();
        registry.addHandler(eventWebSocketHandler(), "/events");
        registry.addHandler(snakeTrainingWebSocketHandler(), "/training");
    }

    @Bean
    public WebSocketHandler echoWebSocketHandler() {
        return new EchoWebSocketHandler();
    }

    @Bean
    public WebSocketHandler eventWebSocketHandler() {
        return new PerConnectionWebSocketHandler(EventSocketHandler.class, false);
    }

    @Bean
    public WebSocketHandler snakeTrainingWebSocketHandler() {
        return new PerConnectionWebSocketHandler(TrainingWebSocketHandler.class, false);
    }

    @Bean
    public GameManager gameManager() {
        return new GameManager(globalEventBus());
    }

    @Bean
    public EventBus globalEventBus() {
        return new EventBus("globalEventBus");
    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(512000);
        container.setMaxBinaryMessageBufferSize(512000);
        return container;
    }

    @Bean
    public WebSocketContainerFactoryBean createWebSocketContainer() {
        WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
        container.setMaxTextMessageBufferSize(512000);
        container.setMaxBinaryMessageBufferSize(512000);
        return container;
    }
}
