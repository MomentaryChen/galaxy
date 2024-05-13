package com.momentary.galaxy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentary.galaxy.enity.Player;

@Repository
public interface PlayerDao extends JpaRepository<Player, Long> {

    // @Query("SELECT p FROM player p WHERE p.id.name = ?1")
    Player findByName(String name);

    // @Query("SELECT p FROM player p WHERE p.id.team = ?1")
    // List<Player> findByTeam(Team team);
    

}
