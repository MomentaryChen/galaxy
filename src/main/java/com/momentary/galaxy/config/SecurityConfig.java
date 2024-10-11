package com.momentary.galaxy.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.momentary.galaxy.GalaxyApplication;
import com.momentary.galaxy.filter.GalaxyAuthenticationFilter;
import com.momentary.galaxy.service.security.GalaxyUserDetails;
import com.momentary.galaxy.service.security.GalaxyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    GalaxyAuthenticationFilter authenticationFilter;

    @Autowired
    private GalaxyUserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        // 不加密
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        SecurityFilterChain chain = httpSecurity
                .cors(Customizer.withDefaults())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        // H2
                        .requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // Websocket
                        .requestMatchers(HttpMethod.GET, "/ws/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ws/**").permitAll()
                        // .requestMatchers(HttpMethod.GET, "/ws/info").permitAll()
                        // System
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/common/**").permitAll()
                        // Player
                        .requestMatchers(HttpMethod.GET, "/api/player/**").hasAuthority("USER")

                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .headers(header -> {
                    header.frameOptions(FrameOptionsConfig::disable);
                })
                .build();

        return chain;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
