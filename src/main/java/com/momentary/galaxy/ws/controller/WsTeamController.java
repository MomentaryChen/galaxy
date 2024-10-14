package com.momentary.galaxy.ws.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.momentary.galaxy.constant.GalaxyConstants;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.req.SelectedTeamReq;
import com.momentary.galaxy.service.LookupService;
import com.momentary.galaxy.service.TokenService;

@Controller
public class WsTeamController {

    private static final Logger logger = LogManager.getLogger(WsTeamController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    LookupService lookupService;

    @MessageMapping("/selectedTeam")
    public Team setSelectedTeam(String req, SimpMessageHeaderAccessor headerAccessor) {
        try {
            // Get Principal from headerAccessor
            String sessionId = headerAccessor.getSessionId();
            String clientId = headerAccessor.getSessionAttributes().get("clientId").toString();
            String userName = headerAccessor.getUser().getName();

            logger.info("sessionId: " + sessionId);
            logger.info("userName: " + userName);
            logger.info("clientId: " + clientId);

            SelectedTeamReq selectedTeamReq = new Gson().fromJson(req,
                    SelectedTeamReq.class);

            System.out.println(req);
            System.out.println(selectedTeamReq.toString());

            lookupService.insertOrUpdateLookUp(
                    GalaxyConstants.TEAM_LOOKUP_TYPE,
                    GalaxyConstants.TEAM_LOOKUP_CODE_PRE_STRING + clientId,
                    "SELECTED TEAM",
                    userName,
                    String.valueOf(selectedTeamReq.getTeamId()),
                    sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @MessageMapping("/exit")
    @Transactional
    public Team exit(String req, SimpMessageHeaderAccessor headerAccessor) {
        try {
            // Get Principal from headerAccessor
            String sessionId = headerAccessor.getSessionId();
            String clientId = headerAccessor.getSessionAttributes().get("clientId").toString();
            String userName = headerAccessor.getUser().getName();

            logger.info("sessionId: " + sessionId);
            logger.info("userName: " + userName);
            logger.info("clientId: " + clientId);

            SelectedTeamReq selectedTeamReq = new Gson().fromJson(req,
                    SelectedTeamReq.class);

            lookupService.removeLookUp(
                    GalaxyConstants.TEAM_LOOKUP_TYPE,
                    GalaxyConstants.TEAM_LOOKUP_CODE_PRE_STRING + clientId);

            logger.info(userName + " exit team " + selectedTeamReq.getTeamId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
