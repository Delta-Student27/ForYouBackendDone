package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
<<<<<<< HEAD
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtFilter;
=======
import org.springframework.http.HttpMethod;
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Link the CORS bean here
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
<<<<<<< HEAD
                    .requestMatchers("/api/products/**").permitAll() // Allow viewing products
                    .requestMatchers("/api/cart/**").authenticated() // Cart must be authenticated
=======
                    .requestMatchers(HttpMethod.GET, "/api/products/**")
                    //.hasAnyRole("USER", "ADMIN")
                    .permitAll()

                    .requestMatchers(HttpMethod.POST, "/api/products/**")
                    .hasRole("ADMIN")
                    //.permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/products/**")
                    .hasRole("ADMIN")
                    //.permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/products/**")
                    .hasRole("ADMIN")
                    //.permitAll()

                     
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); 
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repo){
        return new CustomUserDetailsService(repo);
    }
}