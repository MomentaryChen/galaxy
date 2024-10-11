package com.momentary.galaxy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // private final GalaxyHandshakeInterceptor galaxyHandshakeInterceptor;
    // private final MyWebSocketHandler webSocketHandler;

    // public WebSocketConfig(GalaxyHandshakeInterceptor galaxyHandshakeInterceptor, MyWebSocketHandler webSocketHandler) {
    //     this.galaxyHandshakeInterceptor = galaxyHandshakeInterceptor;
    //     this.webSocketHandler = webSocketHandler;
    // }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new GalaxyHandshakeInterceptor())
                .setAllowedOrigins("http://localhost:4500").withSockJS();
    }

}
