package com.momentary.galaxy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.enity.Team;

public interface TeamDao extends JpaRepository<Team, Long> {

    Team findByName(String name);

}
