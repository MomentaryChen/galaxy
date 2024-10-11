package com.momentary.galaxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.LookupDao;
import com.momentary.galaxy.enity.Lookup;

public interface LookupService {

    public Lookup insertOrUpdateLookUp(String lookupType, String lookupCode, String LookupDesc, String lookupValue1, String lookupValue2);

}
