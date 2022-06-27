package com.backend.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/queue/errors").permitAll()
                .simpDestMatchers("/app/**").permitAll()
                .simpSubscribeDestMatchers("/topic/warn-logs").hasAuthority("RECEIVE_NOTIFICATION_LOGS")
                .anyMessage().denyAll();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic/", "/queue/"); // Server sends messages on routes that are defined here.
        config.setApplicationDestinationPrefixes("/api/app"); // Clients use this prefix when they send message to server.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/websocket").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
