package com.siteshkumar.student_management_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // public api's
            .requestMatchers(HttpMethod.GET, "/courses/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

            // api that can access by students
            .requestMatchers("/students/me").hasRole("STUDENT")
            .requestMatchers("/auth/change-password").hasRole("STUDENT")

            // api that can be accessed by teachers and admin
            .requestMatchers(HttpMethod.GET, "/students/**").hasAnyRole("TEACHER", "ADMIN")
            .requestMatchers("/enrollments/**").hasAnyRole("TEACHER", "ADMIN")

            // Api that can only be accessed by admin
            .requestMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/students/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/students/**").hasRole("ADMIN")

            .requestMatchers(HttpMethod.POST, "/courses").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/courses/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/courses/**").hasRole("ADMIN")
            .requestMatchers("/auth/signup").hasRole("ADMIN")

            .requestMatchers("/reports/**").hasRole("ADMIN")

            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
   