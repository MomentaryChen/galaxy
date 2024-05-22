package com.momentary.galaxy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.momentary.galaxy.BaseTest;
import com.momentary.galaxy.modal.req.PlayerReq;

public class PasswordEncoderTests extends BaseTest{
    private final static Logger logger = LogManager.getLogger(PlayerServiceTests.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test  
    public void encodeTest() throws Exception {
        String a = passwordEncoder.encode("123456");
        String b = passwordEncoder.encode("123456");

        System.out.println(passwordEncoder.matches("123456", a));
        System.out.println(passwordEncoder.matches(b, a));
    }
    
}
