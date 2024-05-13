package com.momentary.galaxy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.constant.HttpRespCode;
import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.modal.BaseRs;
import com.momentary.galaxy.modal.res.LevelRes;
import com.momentary.galaxy.modal.res.PlayerRes;
import com.momentary.galaxy.service.LevelService;
import com.momentary.galaxy.service.PlayerService;

import ch.qos.logback.classic.Level;

@RestController
@RequestMapping("/api/level")
public class LevelController {
    @Autowired
    LevelService levelController;
    
    @GetMapping("all")
    @MessageMapping("/levels")
    @SendTo("/topic/levels")
    public ResponseEntity<BaseRs> getLevels() {
        System.out.println("Get all levels");
        BaseRs res = new BaseRs();
        res.setCode(HttpRespCode.Common.SUCCESS.getCode());
        res.setMsg(HttpRespCode.Common.SUCCESS.getMsg());
        res.setData(levelController.getLevels());

        return ResponseEntity.ok(res);
    }
}
