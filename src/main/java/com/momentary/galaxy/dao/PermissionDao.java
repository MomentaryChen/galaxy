package com.momentary.galaxy.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.momentary.galaxy.enity.Permission;

@Repository
public interface PermissionDao extends JpaRepository<Permission, Long> {


    Permission findByName(String name);

    Permission findByNameAndOperator(String name, String operator);

}
