package com.momentary.galaxy.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.momentary.galaxy.dao.RoleDao;
import com.momentary.galaxy.dao.SystemDao;
import com.momentary.galaxy.enity.ApiClient;
import com.momentary.galaxy.enity.GalaxyRole;

@Component
public class CreateAdmin implements CommandLineRunner {
    @Autowired
    SystemDao SystemDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        GalaxyRole admin = GalaxyRole.builder()
                        .id(1L)
                        .name("ADMIN")
                        .build();
        GalaxyRole user = GalaxyRole.builder()
                        .id(2L)
                        .name("USER")
                        .build();
        List<GalaxyRole> roles = new ArrayList<GalaxyRole>(){
            {
                // add(admin);
                add(user);
            }
        };

        ApiClient apiClient = ApiClient
                .builder()
                .id("GalaxyApAdmin")
                .username("Victor")
                .userSecret(passwordEncoder.encode("zxcv1234"))
                .user_enable(true)
                .roles(roles)
                .build();

        SystemDao.save(apiClient);

        System.out.println("初始化創建完成");
    }
}