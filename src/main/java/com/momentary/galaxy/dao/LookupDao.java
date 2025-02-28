package com.momentary.galaxy.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentary.galaxy.enity.Level;
import com.momentary.galaxy.enity.Lookup;

import java.util.List;

@Repository
public interface LookupDao extends JpaRepository<Lookup, Long> {

    Lookup findByLookupTypeAndLookupCode(String lookupType, String lookupCode);

    List<Lookup> findByLookupType(String lookupType);

    Long deleteByLookupTypeAndLookupCode(String lookupType, String lookupCode);
}
