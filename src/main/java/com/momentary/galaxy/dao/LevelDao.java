package com.momentary.galaxy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentary.galaxy.enity.Level;

@Repository
public interface LevelDao extends JpaRepository<Level, Integer> {

}
