package org.konge.taskmanagementapp.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception {
        //CSRF disabled because it's not possible because of the absence of cookies
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //auth resources and error messages are accessible without authentication
                        .requestMatchers("/api/auth/**", "/error")
                        .permitAll()
                        //all other requests require authentication
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
