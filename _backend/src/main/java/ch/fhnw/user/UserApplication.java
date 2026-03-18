package ch.fhnw.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.fhnw.user.business.service.UserService;
import ch.fhnw.user.data.domain.User;
import io.swagger.v3.oas.annotations.Hidden;

@SpringBootApplication
@Hidden
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserService userService) {
        return args -> {
            if (userService.getAllUsers().isEmpty()) {
                User admin = new User();
                admin.setUsername("nikola.admin");
                admin.setEmail("nikola.matovic@fhnw.ch");
                admin.setPasswordHash("admin123");
                admin.setDisplayName("Nikola Matovic");
                admin.setCreatedAt(LocalDateTime.now());

                User standardUser = new User();
                standardUser.setUsername("luca.user");
                standardUser.setEmail("luca.masella@fhnw.ch");
                standardUser.setPasswordHash("user123");
                standardUser.setDisplayName("Luca Masella");
                standardUser.setCreatedAt(LocalDateTime.now());

                userService.createUser(admin);
                userService.createUser(standardUser);
            }
        };
    }
}