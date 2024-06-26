package com.momentary.galaxy.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.momentary.galaxy.dao.PermissionDao;
import com.momentary.galaxy.enity.Permission;

@Component
@Order(1)
public class CreatePermission implements CommandLineRunner {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--------Start CreatePermission--------");
        try {
            String function = "/function";
            List<Permission> permissions = new ArrayList<>();

            Permission f_read = Permission.builder().name("Function").desc("Function > Read").operator("Read")
                    .url(function)
                    .build();
            Permission f_write = Permission.builder().name("Function").desc("Function > Write").operator("Write")
                    .url(function).build();
            Permission f_delete = Permission.builder().name("Function").desc("Function > Delete").operator("Delete")
                    .url(function).build();

            permissions.add(f_read);
            permissions.add(f_write);
            permissions.add(f_delete);

            Permission b_read = Permission.builder().name("Badminton").desc("Badminton > Read").operator("Read")
                    .url(function)
                    .build();
            Permission b_write = Permission.builder().name("Badminton").desc("Badminton > Write").operator("Write")
                    .url(function).build();
            Permission b_delete = Permission.builder().name("Badminton").desc("Badminton > Delete").operator("Delete")
                    .url(function).build();

            permissions.add(b_read);
            permissions.add(b_write);
            permissions.add(b_delete);

            permissionDao.saveAll(permissions);
            System.out.println("--------End CreatePermission--------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
