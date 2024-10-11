package com.momentary.galaxy.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@EnableScheduling
@RestController
public class BamintonController {

    @Autowired
    LevelController levelController;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/baminton")
    public String baminton() {
        return "baminton";
    }

    // @Scheduled(fixedRate = 5000)
    public void autoGreeting() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } // simulated delay
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 獲取當前用戶的 Principal
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        System.out.println("scheduled: " + username);
        // this.template.convertAndSend("/topic/greetings", "Hello");

        this.template.convertAndSendToUser(username, "/topic/greetings", "Hello");
    }
}
