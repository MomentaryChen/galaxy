package com.momentary.galaxy.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.momentary.galaxy.dao.PermissionDao;
import com.momentary.galaxy.dao.RoleDao;
import com.momentary.galaxy.dao.SystemDao;
import com.momentary.galaxy.enity.ApiClient;
import com.momentary.galaxy.enity.GalaxyRole;
import com.momentary.galaxy.enity.Permission;

@Component
@Order(3)
public class CreateAdmin implements CommandLineRunner {
    @Autowired
    SystemDao SystemDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("--------Start CreateAdmin--------");
        try {

            List<GalaxyRole> roles = roleDao.findAll();

            ApiClient apiClient = ApiClient
                    .builder()
                    .id("GalaxyApAdmin")
                    .username("Victor")
                    .userSecret(passwordEncoder.encode("zxcv1234"))
                    .user_enable(true)
                    .roles(roles)
                    .build();

            SystemDao.save(apiClient);

            System.out.println("--------End CreateAdmin--------");

            ApiClient userClient = ApiClient
                    .builder()
                    .id("User")
                    .username("User")
                    .userSecret(passwordEncoder.encode("zxcv1234"))
                    .user_enable(true)
                    .roles(Arrays.asList(roles.get(1)))
                    .build();

            SystemDao.save(userClient);

            System.out.println("初始化創建完成");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}