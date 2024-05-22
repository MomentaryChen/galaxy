package com.momentary.galaxy.service.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.momentary.galaxy.dao.SystemDao;
import com.momentary.galaxy.enity.ApiClient;

@Service
public class GalaxyUserDetailsService implements UserDetailsService {

    @Autowired
    SystemDao SystemDao;

    public GalaxyUserDetails loadUserByClientId(String clientId) throws UsernameNotFoundException {

        ApiClient apiClient = SystemDao.findApiClientById(clientId);

        if (apiClient == null) {
            throw new UsernameNotFoundException(clientId + " is not found");
        }

        return GalaxyUserDetails.builder()
                .clientId(apiClient.getId())
                .password(apiClient.getUserSecret())
                .authorities(apiClient.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
