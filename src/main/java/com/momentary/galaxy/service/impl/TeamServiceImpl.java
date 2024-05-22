package com.momentary.galaxy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.TeamDao;
import com.momentary.galaxy.enity.Court;
import com.momentary.galaxy.enity.Level;
import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.req.PlayerReq;
import com.momentary.galaxy.modal.req.TeamReq;
import com.momentary.galaxy.service.CourtService;
import com.momentary.galaxy.service.LevelService;
import com.momentary.galaxy.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {
    private static final Logger logger = LogManager.getLogger(TeamServiceImpl.class);

    @Autowired
    TeamDao teamDao;

    @Autowired
    LevelService levelService;

    @Autowired
    CourtService courtService;

    @Override
    public Team getTeamById(Long id) throws Exception {

        Team team = teamDao.findById(id).orElseThrow(() -> new Exception("Team is not found"));
        // Trigger lazy loading
        if (team != null) {
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

        try {
            logger.info("Starting to insert team");
            // Set team name
            logger.info("Setting team name ...");
            team.setName(req.getName());
            logger.info("Setting team name successfully");

            // Create players
            logger.info("Setting team players ...");
            List<Player> players = new ArrayList<>();
            for (PlayerReq p : req.getPlayers()) {
                Level level = levelService.getLevel(p.getLevel());
            
                Player newP = new Player();
                newP.setName(p.getName());
                newP.setLevel(level);
                newP.setTeam(team);
                players.add(newP);
            }
            team.setPlayers(players);
            logger.info("Setting team players successfully");

            for(int i = 0; i< req.getTeamNumbers(); i++) {
                Court court = new Court();
                court.setName(String.valueOf(i));
                court.setTeam(team);
            }

            // Set team name
            Team res = teamDao.save(team);
            logger.info("Insert team successfully");
            return res;
        } catch (Exception e) {
            logger.error("Insert team error: {}", e.getMessage());
            throw e;
        }

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
