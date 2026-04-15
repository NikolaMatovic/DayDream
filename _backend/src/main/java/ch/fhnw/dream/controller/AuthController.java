package ch.fhnw.dream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import ch.fhnw.dream.business.service.UserService;
import ch.fhnw.dream.data.domain.User;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request, HttpServletRequest httpRequest) {
        try {
            if (request.getUsername() == null || request.getUsername().isBlank()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Username ist erforderlich"));
            }

            if (request.getEmail() == null || request.getEmail().isBlank()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email ist erforderlich"));
            }

            if (request.getPassword() == null || request.getPassword().isBlank()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Passwort ist erforderlich"));
            }

            if (request.getPassword().length() < 8) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Passwort muss mindestens 8 Zeichen haben"));
            }

            if (userService.getUserByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Username bereits vorhanden"));
            }

            if (userService.getUserByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Email bereits registriert"));
            }

            User user = new User();
            user.setUsername(request.getUsername().trim());
            user.setEmail(request.getEmail().trim());
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

            User savedUser = userService.createUser(user);

            authenticateUser(savedUser.getUsername(), "USER", httpRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                "USER",
                "Registrierung erfolgreich"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Registrierung fehlgeschlagen"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            // Finde User nach Username
            var userOpt = userService.getUserByUsername(request.getUsername());

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Username oder Passwort falsch"));
            }

            User user = userOpt.get();

            // Prüfe Passwort
            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Username oder Passwort falsch"));
            }

            // Login erfolgreich
            String role = "admin".equals(user.getUsername()) ? "ADMIN" : "USER";

            authenticateUser(user.getUsername(), role, httpRequest);

            return ResponseEntity.ok(new AuthResponse(
                user.getId(),
                user.getUsername(),
                role,
                "Login erfolgreich"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Login fehlgeschlagen"));
        }
    }

    private void authenticateUser(String username, String role, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                username,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        httpRequest.getSession(true).setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
        );
    }

    // DTOs
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class SignupRequest {
        private String username;
        private String email;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class AuthResponse {
        private Long userId;
        private String username;
        private String role;
        private String message;

        public AuthResponse(Long userId, String username, String role, String message) {
            this.userId = userId;
            this.username = username;
            this.role = role;
            this.message = message;
        }

        public Long getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
