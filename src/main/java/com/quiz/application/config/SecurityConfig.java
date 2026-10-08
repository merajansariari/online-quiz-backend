package com.quiz.application.config;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.quiz.application.security.JwtAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    // =========================================
    // CORS CONFIGURATION
    // =========================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
        	    "http://localhost:5173",
        	    "http://localhost:5174",
        	    "https://online-quiz-frontend.vercel.app"
        	));

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            // REST API does not use browser sessions
            .csrf(csrf -> csrf.disable())

            // Enable CORS
            .cors(cors -> cors.configurationSource(
                    corsConfigurationSource()
            ))

            // JWT based authentication
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // =========================================
                // PUBLIC ENDPOINTS
                // =========================================

            		.requestMatchers("/api/auth/login").permitAll()
            		.requestMatchers("/api/users/register").permitAll()
            		.requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyRole("USER", "ADMIN")

                // =========================================
                // QUIZ ENDPOINTS
                // =========================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/quizzes/**"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/quizzes/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/quizzes/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/quizzes/**"
                ).hasRole("ADMIN")

                // =========================================
                // QUESTION ENDPOINTS
                // =========================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/questions/**"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/questions/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/questions/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/questions/**"
                ).hasRole("ADMIN")

                // =========================================
                // QUIZ ATTEMPT ENDPOINTS
                // =========================================

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/attempts"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/attempts/*/answers"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/attempts/user"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/attempts/quiz/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/attempts/*/result"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/attempts/*"
                ).hasAnyRole("USER", "ADMIN")

                // =========================================
                // EVERYTHING ELSE
                // =========================================

                .anyRequest().authenticated()
            )

            // =========================================
            // JWT FILTER
            // =========================================

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}

