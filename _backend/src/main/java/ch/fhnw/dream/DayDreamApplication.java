package ch.fhnw.dream;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.fhnw.dream.business.service.UserService;
import ch.fhnw.dream.business.service.DaydreamService;

import ch.fhnw.dream.data.domain.*;

import io.swagger.v3.oas.annotations.Hidden;

@SpringBootApplication
@Hidden
public class DayDreamApplication {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(DayDreamApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserService userService, DaydreamService daydreamService)  {
        return args -> {
            User user1 = createUserIfMissing(
                userService,
                "nikola",
                "nikola@daydream.com",
                "password",
                "Nikola User"
            );

            User user2 = createUserIfMissing(
                userService,
                "luca",
                "luca@daydream.com",
                "password",
                "Luca User"
            );

            createUserIfMissing(
                userService,
                "admin",
                "admin@daydream.com",
                "password",
                "Admin User"
            );


                Daydream daydream1 = new Daydream();
                daydream1.setTitle("Beach Vacation");
                daydream1.setDescription("A relaxing day at the beach");
                daydream1.setUser(user1);

                // Add comments to daydream1
                Comment comment1 = new Comment();
                comment1.setContent("Looks amazing! Wish I was there.");
                comment1.setUser(user2);
                comment1.setDaydream(daydream1);

                Comment comment2 = new Comment();
                comment2.setContent("Don't forget sunscreen!");
                comment2.setUser(user2);
                comment2.setDaydream(daydream1);

                daydream1.setComments(List.of(comment1, comment2));

                // Add tags to daydream1
                Tag tag1 = new Tag();
                tag1.setName("beach");
                tag1.setDaydream(daydream1);

                Tag tag2 = new Tag();
                tag2.setName("relax");
                tag2.setDaydream(daydream1);

                daydream1.setTags(List.of(tag1, tag2));

                Daydream daydream2 = new Daydream();
                daydream2.setTitle("Mountain Hiking");
                daydream2.setDescription("Exploring mountain trails");
                daydream2.setUser(user2);

                // Add comments to daydream2
                Comment comment3 = new Comment();
                comment3.setContent("Watch out for bears!");
                comment3.setUser(user1);
                comment3.setDaydream(daydream2);

                daydream2.setComments(List.of(comment3));

                // Add tags to daydream2
                Tag tag3 = new Tag();
                tag3.setName("mountain");
                tag3.setDaydream(daydream2);

                Tag tag4 = new Tag();
                tag4.setName("adventure");
                tag4.setDaydream(daydream2);

                daydream2.setTags(List.of(tag3, tag4));

                daydreamService.createDaydream(daydream1);
                daydreamService.createDaydream(daydream2);

        };
    }

    private User createUserIfMissing(
            UserService userService,
            String username,
            String email,
            String password,
            String displayName
    ) {
        return userService.getUserByUsername(username).orElseGet(() -> {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setDisplayName(displayName);
            user.setPasswordHash(PASSWORD_ENCODER.encode(password));
            return userService.createUser(user);
        });
    }
}
