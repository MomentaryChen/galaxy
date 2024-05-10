package com.momentary.galaxy.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.message.HelloMessage;
import com.momentary.galaxy.modal.Greeting;

@RestController
public class bamintonController {

    @GetMapping("/baminton")
    public String baminton() {
        return "baminton";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println(message.toString());
        return new Greeting("Hello, " + message.getName() + "!");
    }
}
