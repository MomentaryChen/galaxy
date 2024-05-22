package com.momentary.galaxy.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.momentary.galaxy.enity.ApiClient;
import com.momentary.galaxy.enity.GalaxyRole;
import com.momentary.galaxy.enity.Player;

@Repository
public interface RoleDao extends JpaRepository<GalaxyRole, Long> {

}
