package com.momentary.galaxy.ws;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.momentary.galaxy.config.GalaxyHandshakeInterceptor;
import com.momentary.galaxy.constant.GalaxyConstants;
import com.momentary.galaxy.service.TokenService;
import com.momentary.galaxy.service.security.GalaxyUserDetails;
import com.momentary.galaxy.service.security.GalaxyUserDetailsService;
import com.momentary.galaxy.ws.controller.WsController;


import com.sun.security.auth.UserPrincipal;

import io.micrometer.common.util.StringUtils;

@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final Logger logger = LogManager.getLogger(GalaxyHandshakeInterceptor.class);

    @Autowired
    private GalaxyUserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WsController wsController;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        String sessionId = accessor.getSessionId();
        

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader(GalaxyConstants.API_HEADER_STRING);

            if (token != null) {
                // print
                logger.info("token = " + token);
                try {
                    authWebSocketToken(token, accessor);
                    logger.error("驗證成功");
                } catch (Exception e) {
                    logger.error("驗證失敗");
                    return null;
                }
            }
        } else if(StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            logger.info("disconnect: " + sessionId);
        }

        return message;
    }

    private void authWebSocketToken(String token, StompHeaderAccessor accessor) throws Exception {
        try {
            // 1. check token
            if (StringUtils.isBlank(token)) {
                throw new Exception("Token is null");
            }
            logger.info("Pass: token is not null");

            // 2. check token is valid or not
            tokenService.validateToken(token);
            logger.info("Pass: Check token is valid or not ");

            // 3. check client id
            String clientId = tokenService.getClientId(token);
            GalaxyUserDetails userDetails = userDetailsService.loadUserByClientId(clientId);
            if (userDetails == null) {
                throw new Exception("User not found");
            }

            accessor.getSessionAttributes().put("clientId", clientId);
            accessor.setUser(new UserPrincipal(userDetails.getUsername()));

        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            throw e;
        }

    }

}
