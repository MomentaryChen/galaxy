package com.momentary.galaxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.message.HelloMessage;
import com.momentary.galaxy.modal.Greeting;

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

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println(message.toString());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    // @Scheduled(fixedRate = 5000)
    public void autoGreeting() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } // simulated delay
        System.out.println("scheduled");
        this.template.convertAndSend("/topic/greetings", "Hello");
        this.template.convertAndSendToUser("admin", "/topic/greetings", "Hello");
    }
}
