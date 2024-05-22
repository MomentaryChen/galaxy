package com.momentary.galaxy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.LevelDao;
import com.momentary.galaxy.enity.Level;
import com.momentary.galaxy.service.LevelService;

@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    LevelDao levelDao;

    @Override
    public List<Level> getLevels() {
        return levelDao.findAll();
    }

    @Override
    public Level getLevel(Integer level) {
        return levelDao.findById((Long.valueOf(level))).orElseThrow();
    }

}
