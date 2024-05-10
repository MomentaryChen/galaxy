package com.momentary.galaxy.service;

import java.util.List;

import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.req.TeamReq;

public interface TeamService {

    public Team getTeamById(Long id) throws Exception;

    public Team getTeamByName(String name);

    public Team insertTeam(TeamReq req) throws Exception;

    public Team updateTeam(Team team) throws Exception;

    public List<Team> getTeams();

}
