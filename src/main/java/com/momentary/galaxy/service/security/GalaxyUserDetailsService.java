package com.momentary.galaxy.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.PermissionDao;
import com.momentary.galaxy.dao.RoleDao;
import com.momentary.galaxy.dao.SystemDao;
import com.momentary.galaxy.enity.ApiClient;
import com.momentary.galaxy.enity.GalaxyRole;
import com.momentary.galaxy.enity.Permission;
import com.momentary.galaxy.modal.res.PermissionVo;

@Service
public class GalaxyUserDetailsService implements UserDetailsService {

    @Autowired
    SystemDao SystemDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    public GalaxyUserDetails loadUserByClientId(String clientId) throws UsernameNotFoundException {
        
        // Get API Client
        ApiClient apiClient = SystemDao.findApiClientById(clientId);

        if (apiClient == null) {
            throw new UsernameNotFoundException(clientId + " is not found");
        }

        List<PermissionVo> permissions = new ArrayList<>();
        for (GalaxyRole r : apiClient.getRoles()) {
            for (Permission p : r.getPermissions()) {
                PermissionVo vo = PermissionVo.builder().operator(p.getOperator()).name(p.getName()).build();
                if (!permissions.contains(vo)) {
                    permissions.add(vo);
                    System.out.println(vo);
                } 
            }
        }

        return GalaxyUserDetails.builder()
                .clientId(apiClient.getId())
                .username(apiClient.getUsername())
                .password(apiClient.getUserSecret())
                .authorities(apiClient.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .permissions(permissions)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
