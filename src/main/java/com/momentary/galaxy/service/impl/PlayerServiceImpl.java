package com.momentary.galaxy.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.constant.PlayerLevelEnum;
import com.momentary.galaxy.dao.PlayerDao;
import com.momentary.galaxy.enity.Level;
import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.enity.Team;
import com.momentary.galaxy.modal.req.PlayerReq;
import com.momentary.galaxy.service.LevelService;
import com.momentary.galaxy.service.PlayerService;
import com.momentary.galaxy.service.TeamService;

import jakarta.transaction.Transactional;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final Logger logger = LogManager.getLogger(PlayerServiceImpl.class);

    @Autowired
    PlayerDao playerDao;

    @Autowired
    TeamService teamService;

    @Autowired
    LevelService levelService;

    @Override
    public Player getPlayerByName(String name) {

        return playerDao.findByName(name);
    }

    @Override
    public List<Player> getPlayers() {
        return playerDao.findAll();
    }

    @Transactional
    @Override
    public Player insertPlayer(PlayerReq res) throws Exception {
        Player newPlayer = new Player();

        try {
            logger.info("String to insert player: " + res.toString());

            newPlayer.setId(101L);
            newPlayer.setName(res.getName());
            newPlayer.setLevel(levelService.getLevel(res.getLevel()));

            Team team = teamService.getTeamById(res.getTeamId());
            newPlayer.setTeam(team);
            
            newPlayer = playerDao.save(newPlayer);
            logger.info("Insert player successfully");
            return newPlayer;
        } catch (Exception e) {

            throw e;
        }
    }

}
