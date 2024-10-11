package com.momentary.galaxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.LookupDao;
import com.momentary.galaxy.enity.Lookup;
import com.momentary.galaxy.service.LookupService;

@Service
public class LookupServiceImpl implements LookupService {

    @Autowired
    private LookupDao lookupDao;

    @Override
    public Lookup insertOrUpdateLookUp(String lookupType, String lookupCode, String LookupDesc,
            String lookupValue1, String lookupValue2) {
        Lookup lookup = lookupDao.findByLookupTypeAndLookupCode(lookupType, lookupCode);

        if (lookup == null) {
            lookup = Lookup.builder().lookupType(lookupType).lookupCode(lookupCode).lookupDesc(LookupDesc).build();
        }

        lookup.setLookupValue1(lookupValue1);
        lookup.setLookupValue2(lookupValue2);

        return lookupDao.save(lookup);
    }

}
