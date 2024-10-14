package com.momentary.galaxy.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.momentary.galaxy.config.GalaxyHandshakeInterceptor;
import com.momentary.galaxy.service.TokenService;
import com.momentary.galaxy.service.security.GalaxyUserDetailsService;
import com.momentary.galaxy.ws.controller.WsController;

/**
 * Referrer: https://blog.csdn.net/zhaoyong_/article/details/114834592
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("websocket-heartheat-pool-");
        taskScheduler.initialize();

        config.enableSimpleBroker("/topic", "/queue", "/user")
            .setHeartbeatValue(new long[] {10000, 10000})
            .setTaskScheduler(taskScheduler);
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
        
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration config) {

        config.setMessageSizeLimit(1024 * 1024);
        config.setSendBufferSizeLimit(1024 * 1024);

        config.setSendTimeLimit(10000);

    }
    

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        registration.taskExecutor()
            .corePoolSize(10)
            .maxPoolSize(20)
            .keepAliveSeconds(60);

        registration.interceptors(getSocketChannelInterceptor());

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                // .setHandshakeHandler(new MyWebSocketHandshakeHandler())
                .addInterceptors(new GalaxyHandshakeInterceptor())
                .setAllowedOrigins("http://localhost:4500").withSockJS();
    }

    @Bean
    public WebSocketChannelInterceptor getSocketChannelInterceptor() {
        return new WebSocketChannelInterceptor();
    }

}
