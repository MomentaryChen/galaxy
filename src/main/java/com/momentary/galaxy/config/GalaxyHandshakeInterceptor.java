package com.momentary.galaxy.config;

import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.momentary.galaxy.constant.GalaxyConstants;
import com.momentary.galaxy.service.TokenService;
import com.momentary.galaxy.service.security.GalaxyUserDetails;
import com.momentary.galaxy.service.security.GalaxyUserDetailsService;

import io.micrometer.common.util.StringUtils;
import org.springframework.http.server.ServerHttpRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GalaxyHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LogManager.getLogger(GalaxyHandshakeInterceptor.class);

    @Autowired
    GalaxyUserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String token = request.getHeaders().getFirst(GalaxyConstants.API_HEADER_STRING);
        String auth = request.getHeaders().getFirst("Authorization");

        HttpHeaders headers = request.getHeaders();
        headers.forEach((key, value) -> {
            System.out.println("key = " + key);
            System.out.println("value = " + value);
        });

        System.out.println("beforeHandshake Token: " + token);
        System.out.println("beforeHandshake Auth: " + auth);


        return true;
    }

    private void authApiToken(String token, HttpServletRequest request) throws Exception {
        try {
            // 1. check token
            if (StringUtils.isBlank(token)) {
                throw new Exception("Token is null");
            }
            logger.info("Pass: check token");

            // 2. check token is valid or not
            tokenService.validateToken(token);
            logger.info("Pass: Check token is valid or not ");

            // 3. check client id
            String clientId = tokenService.getClientId(token);
            GalaxyUserDetails userDetails = userDetailsService.loadUserByClientId(clientId);
            if (userDetails == null) {
                throw new Exception("User not found");
            }
            logger.info("Pass: check client id ");
            logger.info(userDetails);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            throw e;
        }

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
        // 握手完成後的邏輯（可選）
    }
}
