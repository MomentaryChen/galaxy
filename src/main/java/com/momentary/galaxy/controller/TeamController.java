package com.momentary.galaxy.controller;

import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.service.TeamService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.req.TeamReq;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("all")
    public List<Team> getTeams() {
        System.out.println("SUL");
        return teamService.getTeams();
    }

    @GetMapping("{id}")
    public Team getPlayerByName(@PathVariable("id") Long id) {
        try {
            return teamService.getTeamById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping
    public String insertTeam(@RequestBody TeamReq team) {
        try {
            teamService.insertTeam(team);
            return String.format("Create %s successfully.", team.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("Create %s failed.", team.toString());
        }
    }

}
