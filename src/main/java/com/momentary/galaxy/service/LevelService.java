package com.momentary.galaxy.service;

import java.util.List;

import com.momentary.galaxy.enity.Level;
import com.momentary.galaxy.enity.Player;

public interface LevelService {
    
    public List<Level> getLevels();

    public Level getLevel(Integer level);
}
