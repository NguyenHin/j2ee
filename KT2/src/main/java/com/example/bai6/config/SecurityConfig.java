package com.example.bai6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import com.example.bai6.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService; // 🔥 thêm dòng này

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .userDetailsService(userDetailsService) // 🔥 dùng service load user

        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/products", "/products/**").permitAll()
                .requestMatchers("/cart/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/login", "/error").permitAll()
                .anyRequest().authenticated()
        )

        .formLogin(form -> form
                .defaultSuccessUrl("/products", true)
                .permitAll()
        )

        .logout(logout -> logout.logoutSuccessUrl("/products"));

        return http.build();
    }
}