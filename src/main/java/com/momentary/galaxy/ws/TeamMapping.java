package com.momentary.galaxy.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.momentary.galaxy.constant.GalaxyConstants;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.req.SelectedTeamReq;
import com.momentary.galaxy.service.LookupService;
import com.momentary.galaxy.service.TokenService;
import java.security.Principal;
@Controller
public class TeamMapping {
    @Autowired
    TokenService tokenService;

    @Autowired
    LookupService lookupService;

    @MessageMapping("/selectedTeam")
    public Team setSelectedTeam(String req, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
        try {
            String token = headerAccessor.getFirstNativeHeader(GalaxyConstants.API_HEADER_STRING);
            String clientId = tokenService.getClientId(token);

            String username = principal.getName();
            System.out.println("Username: " + username);

            SelectedTeamReq selectedTeamReq = new Gson().fromJson(req, SelectedTeamReq.class);

            lookupService.insertOrUpdateLookUp("Team", "SELECTED_TEAM_" + clientId, "SELECTED TEAM",
                    String.valueOf(selectedTeamReq.getTeamId()), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
