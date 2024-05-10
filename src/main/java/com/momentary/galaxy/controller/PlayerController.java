package com.momentary.galaxy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.constant.HttpRespCode;
import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.modal.req.PlayerReq;
import com.momentary.galaxy.modal.res.PlayerRes;
import com.momentary.galaxy.service.PlayerService;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("all")
    public List<Player> getPlayers(Player p) {
        return playerService.getPlayers();
    }

    @PostMapping("")
    public String inserPlayer(@RequestBody PlayerReq p) {
        try {
            System.out.println(p.toString());
            Player a = playerService.insertPlayer(p);

            return String.format("Create %s successfully.", a.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("Create %s failed.", p.getName());
        }
    }

    @GetMapping("{name}")
    public ResponseEntity<PlayerRes> getPlayerByName(@PathVariable("name") String name) {
        PlayerRes res = new PlayerRes();
        res.setCode(HttpRespCode.Common.SUCCESS.getCode());
        res.setMsg(HttpRespCode.Common.SUCCESS.getMsg());
        res.setPlayer(playerService.getPlayerByName(name));

        return ResponseEntity.ok(res);
    }

}
