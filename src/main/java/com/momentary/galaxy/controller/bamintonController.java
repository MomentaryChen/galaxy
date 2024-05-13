package com.momentary.galaxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.message.HelloMessage;
import com.momentary.galaxy.modal.Greeting;

@RestController
public class bamintonController {

    @Autowired
    LevelController levelController;

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

    // @MessageMapping("/level")
    // @SendTo("/topic/Level")
    // public Greeting getLevel() throws Exception {
    //     return levelController.getLevels();
    // }
}
