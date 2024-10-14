package com.momentary.galaxy.ws.controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@MessageMapping("/api/ws")
public class WsController {

    // private final SimpUserRegistry simpUserRegistry;

    // public WsController(SimpUserRegistry simpUserRegistry) {
    //     this.simpUserRegistry = simpUserRegistry;
    // }

    private final ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    // @MessageMapping("/all")
    // public List<String> activeSessions() {
    //     return simpUserRegistry
    //             .getUsers()
    //             .stream()
    //             .map(SimpUser::getName)
    //             .collect(Collectors.toList());
    // }

    // Add Socket to map
    public void addSocket(String key, String value) {
        map.put(key, value);
    }

    // Remove Socket from map
    public void removeSocket(String key) {
        map.remove(key);
    }

}
