package com.andrei.LibraryManager.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.andrei.LibraryManager.config.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


  private final JwtFilter JWT_FILTER;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            auth -> auth.requestMatchers("/auth/**").permitAll()
                .requestMatchers("/library/**").hasAnyAuthority("CLIENT_ROLE")
                .requestMatchers("/manager/**").hasAnyAuthority("MANAGER_ROLE",
                    "ADMIN_ROLE")
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN_ROLE")
                .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .addFilterBefore(JWT_FILTER, UsernamePasswordAuthenticationFilter.class);
    http.csrf(crsf -> crsf.disable());
    return http.build();

  }
}
