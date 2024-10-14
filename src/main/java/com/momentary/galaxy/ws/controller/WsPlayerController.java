package com.momentary.galaxy.ws.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.momentary.galaxy.enity.Lookup;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.service.LookupService;
import com.momentary.galaxy.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WsPlayerController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SimpMessagingTemplate templateToUser;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private TeamService teamService;


    @Scheduled(fixedRate = 5000)
    public void refreshWaitingPlayer() throws NumberFormatException, Exception {
        List<Lookup> lookups = lookupService.findByWatingList();
        log.info("lookups: " + lookups.size());
        for (Lookup lookup : lookups) {
            Team team = teamService.getTeamById(Long.valueOf(lookup.getLookupValue2()));

            if (null != team) {
                log.info("Send Message to " + lookup.getLookupValue1());
                template.convertAndSendToUser(lookup.getLookupValue1(), "/topic/waitingPlayers", team);
                // template.convertAndSend("/topic/waitingPlaers", team);
            }
        }
    }

    @MessageMapping("/sendMessageToUser")
    public void sendMessageToUser(String message, String username) {
        // 向特定的用戶發送消息
        template.convertAndSendToUser(username, "/queue/private", message);
    }

}
