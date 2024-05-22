package com.momentary.galaxy.controller;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.constant.GalaxyConstants;
import com.momentary.galaxy.constant.HttpRespCode;
import com.momentary.galaxy.modal.ApiToken;
import com.momentary.galaxy.modal.req.GenTokenReqVO;
import com.momentary.galaxy.modal.res.GenTokenResVo;
import com.momentary.galaxy.service.TokenService;
import com.momentary.galaxy.service.security.GalaxyUserDetails;
import com.momentary.galaxy.service.security.GalaxyUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class SystemController {
    private static final Logger logger = LogManager.getLogger(SystemController.class);
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private GalaxyUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("token")
    public ResponseEntity<GenTokenResVo> genApiToken(@RequestBody GenTokenReqVO req, HttpServletRequest request) {
        logger.info("============ Start SystemController.genApiToken() ============");
        GenTokenResVo res = null;
        try {
            // 1. search user id is exist or not
            GalaxyUserDetails userDetails = userDetailsService.loadUserByClientId(req.getClientId());
            System.out.println(userDetails);

            // 2. check password
            if(!passwordEncoder.matches(req.getClientSecret(), userDetails.getPassword())) {
                res = new GenTokenResVo();
                res.setCode(HttpRespCode.Common.FAILURE.getCode());
                res.setMsg(HttpRespCode.Common.FAILURE.getMsg());
                res.setToken(null);
                return ResponseEntity.ok(res);
            }

            // 2. gen jwt token
            ApiToken token = tokenService.generateToken(userDetails.getClientId(), 
                LocalDateTime.now(), 
                LocalDateTime.now().plusMinutes(GalaxyConstants.EXPIRATION_TIME));

            // 
            res = new GenTokenResVo();
            res.setCode(HttpRespCode.Common.SUCCESS.getCode());
            res.setMsg(HttpRespCode.Common.SUCCESS.getMsg());
            res.setToken(token.getAccess());

        } catch(Exception e) {
            res = new GenTokenResVo();
            res.setCode(HttpRespCode.Common.FAILURE.getCode());
            res.setMsg(HttpRespCode.Common.FAILURE.getMsg());
            res.setToken(null);

        } finally {
            logger.info("============ End SystemController.genApiToken() ============");
        }

        return ResponseEntity.ok(res);
    }
    
}
