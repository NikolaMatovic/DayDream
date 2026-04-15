package ch.fhnw.dream.security;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Users are managed via database (User entity) and startup initialization in DayDreamApplication
    // No in-memory user store needed anymore

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> auth
                        // Public endpoints
                        .requestMatchers(
                            "/v1/auth/**",           // Login/Signup public
                            "/",                      // React app root
                            "/index.html",
                            "/assets/**",            // Vite frontend assets
                            "/favicon.ico",
                            "/swagger-ui.html",       // Swagger UI
                            "/v3/api-docs/**",        // API documentation
                            "/swagger-ui/**",         // Swagger UI resources
                            "/h2-console/**"          // H2 console
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                            "/login",
                            "/signup",
                            "/feed",
                            "/create",
                            "/profile",
                            "/daydreams/**"
                        ).permitAll()
                        
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(401);
                            response.getWriter().write("{\"message\":\"Unauthorized: Please login first\"}");
                        })
                )   
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // For H2 console
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build(); 
    }
}
