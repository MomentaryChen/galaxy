package com.momentary.galaxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.CourtDao;
import com.momentary.galaxy.enity.Court;
import com.momentary.galaxy.service.CourtService;

@Service
public class CourtServiceImpl implements CourtService {

    @Autowired
    CourtDao courtDao;

    @Override
    public Court insertCourt(Court court) throws Exception {
        return courtDao.save(court);
    }

}
