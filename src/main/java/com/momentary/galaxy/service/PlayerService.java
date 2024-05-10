package com.momentary.galaxy.service;

import java.util.List;

import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.modal.req.PlayerReq;

public interface PlayerService {


    /*
     * get players
     */

     public List<Player> getPlayers();

    /*
     * get player by name
     */

    public Player getPlayerByName(String name);

    /*
     * insert player
     */
    public Player insertPlayer(PlayerReq res) throws Exception;
}
