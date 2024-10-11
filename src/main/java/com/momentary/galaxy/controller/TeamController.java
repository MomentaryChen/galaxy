package com.momentary.galaxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.constant.GalaxyConstants;
import com.momentary.galaxy.constant.HttpRespCode;
import com.momentary.galaxy.enity.Court;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.BaseRs;
import com.momentary.galaxy.modal.req.SelectedTeamReq;
import com.momentary.galaxy.modal.req.TeamReq;
import com.momentary.galaxy.service.CourtService;
import com.momentary.galaxy.service.LookupService;
import com.momentary.galaxy.service.TeamService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    CourtService courtService;

    @Autowired
    LookupService LookupService;

    @GetMapping("")
    @MessageMapping("/teams")
    @SendTo("/topic/teams")
    public ResponseEntity<BaseRs> getTeams() {
        BaseRs res = new BaseRs();
        res.setCode(HttpRespCode.Common.SUCCESS.getCode());
        res.setMsg(HttpRespCode.Common.SUCCESS.getMsg());
        res.setData(teamService.getTeams());

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public Team getPlayerByName(@PathVariable("id") Long id) {
        try {
            return teamService.getTeamById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping
    public String insertTeam(@RequestBody TeamReq teamReq) {
        try {
            // 1. 建立teams
            Team newTeam = teamService.insertTeam(teamReq);

            // 2. 建立場地
            for (int i = 0; i < teamReq.getCourtCnt(); i++) {
                // 2.1 建立場地
                Court court = new Court();
                court.setName(String.valueOf(i + 1));
                court.setTeam(newTeam);

                courtService.insertCourt(court);
            }

            return String.format("Create %s successfully.", teamReq.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("Create %s failed.", teamReq.toString());
        }
    }

}
