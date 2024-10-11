package com.momentary.galaxy.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import com.momentary.galaxy.service.LookupService;

public class PlayerMapping {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SimpMessagingTemplate templateToUser;

    @Autowired
    private LookupService lookupService;

     @Scheduled(fixedRate = 5000)
     public void refreshWaitingPlayer() {


        
    }

    @MessageMapping("/sendMessageToUser")
    public void sendMessageToUser(String message, String username) {
        // 向特定的用戶發送消息
        template.convertAndSendToUser(username, "/queue/private", message);
    }
    
}
