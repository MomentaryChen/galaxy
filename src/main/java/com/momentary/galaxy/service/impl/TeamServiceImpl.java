package com.momentary.galaxy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.TeamDao;
import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.req.TeamReq;
import com.momentary.galaxy.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamDao teamDao;

    @Override
    public Team getTeamById(Long id) throws Exception {

        Team team = teamDao.findById(id).orElseThrow(() -> new Exception("Team is not found"));
        // Trigger lazy loading
        if(team != null) {
            team.getPlayers().size();
        }
        return team;
    }

    @Override
    public Team getTeamByName(String name) {
        return teamDao.findByName(name);
    }

    @Override
    public Team insertTeam(TeamReq req) throws Exception {
        Team team = new Team();
        team.setName(req.getName());

        // List<Player> players = new ArrayList<>();
        // for(Player p : req.getPlayers()) {
        //     players.add(new Player(p.getName(), team)))
        // }
        // team.setPlayers(req.getPlayers());
        
        return teamDao.save(team);
    }

    @Override
    public List<Team> getTeams() {
        return teamDao.findAll();
    }

    @Override
    public Team updateTeam(Team team) throws Exception {
        return teamDao.save(team);
    }

}
