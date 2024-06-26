package com.momentary.galaxy.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
@Order(2)
public class CreateRole implements CommandLineRunner {

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--------Start CreateRole--------");
        try {
        List<Permission> all = permissionDao.findAll();

        GalaxyRole admin = GalaxyRole.builder()
                .name("ADMIN")
                .permissions(all)
                .build();

        roleDao.save(admin);

        Permission f_read = permissionDao.findByNameAndOperator("Function", "Read");
        GalaxyRole user = GalaxyRole.builder()
                .name("USER")
                .permissions(Arrays.asList(f_read))
                .build();

        roleDao.save(user);
        System.out.println("--------End CreateRole--------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}