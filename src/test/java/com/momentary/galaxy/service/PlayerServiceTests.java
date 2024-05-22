package com.momentary.galaxy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.momentary.galaxy.BaseTest;
import com.momentary.galaxy.GalaxyApplication;
import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.modal.req.PlayerReq;
import com.momentary.galaxy.service.impl.PlayerServiceImpl;

public class PlayerServiceTests extends BaseTest{
    private final static Logger logger = LogManager.getLogger(PlayerServiceTests.class);

    @Autowired
    PlayerService PlayerService;

    @Autowired
    LevelService levelService;

    @Test
    public void insertPlayerTest() throws Exception {
        PlayerReq p = new PlayerReq();
        p.setName("Test");
        p.setLevel(1);
        p.setTeamId(1L);
        PlayerService.insertPlayer(p);
    }
}
