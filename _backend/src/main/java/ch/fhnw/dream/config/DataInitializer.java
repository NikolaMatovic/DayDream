package ch.fhnw.dream.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ch.fhnw.dream.business.service.UserService;
import ch.fhnw.dream.data.domain.User;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        // Only predefined accounts are allowed to log in.
        createTestUser("nikola", "nikola@daydream.com", "password", "Nikola User");
        createTestUser("luca",   "luca@daydream.com", "password", "Luca User");
        createTestUser("admin",  "admin@daydream.com", "password", "Admin User");
    }

    private void createTestUser(String username, String email, String password, String displayName) {
        // Prüfe ob User bereits existiert
        if (userService.getUserByUsername(username).isPresent()) {
            System.out.println("User '" + username + "' already exists, skipping creation.");
            return;
        }

        // Erstelle neuen User
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setDisplayName(displayName);
        user.setPasswordHash(passwordEncoder.encode(password));

        userService.createUser(user);
        System.out.println("Created test user: " + username);
    }
}
