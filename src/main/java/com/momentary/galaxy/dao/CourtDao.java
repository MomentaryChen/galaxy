package com.momentary.galaxy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentary.galaxy.enity.Court;
    
@Repository
public interface CourtDao extends JpaRepository<Court, Long> {

}

