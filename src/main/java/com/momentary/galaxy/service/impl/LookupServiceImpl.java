package com.momentary.galaxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.constant.GalaxyConstants;
import com.momentary.galaxy.dao.LookupDao;
import com.momentary.galaxy.enity.Lookup;
import com.momentary.galaxy.service.LookupService;

import java.util.List;

@Service
public class LookupServiceImpl implements LookupService {

    @Autowired
    private LookupDao lookupDao;

    @Override
    public Lookup insertOrUpdateLookUp(String lookupType, String lookupCode, String LookupDesc,
            String lookupValue1, String lookupValue2, String lookupValue3) {
        Lookup lookup = lookupDao.findByLookupTypeAndLookupCode(lookupType, lookupCode);

        if (lookup == null) {
            lookup = Lookup.builder().lookupType(lookupType).lookupCode(lookupCode).lookupDesc(LookupDesc).build();
        }

        lookup.setLookupValue1(lookupValue1);
        lookup.setLookupValue2(lookupValue2);
        lookup.setLookupValue3(lookupValue3);

        return lookupDao.save(lookup);
    }

    @Override
    public List<Lookup> findAll() {
        return lookupDao.findAll();
    }

    @Override
    public List<Lookup> findByWatingList() {

        return lookupDao.findByLookupType(GalaxyConstants.TEAM_LOOKUP_TYPE);
    }

    @Override
    public Long removeLookUp(String lookupType, String lookupCode) {
        return lookupDao.deleteByLookupTypeAndLookupCode(lookupType, lookupCode);
    }

}
