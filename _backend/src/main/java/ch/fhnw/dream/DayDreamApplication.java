package ch.fhnw.dream;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.fhnw.dream.business.service.UserService;
import ch.fhnw.dream.business.service.DaydreamService;

import ch.fhnw.dream.data.domain.*;

import io.swagger.v3.oas.annotations.Hidden;

@SpringBootApplication
@Hidden
public class DayDreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayDreamApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserService userService, DaydreamService daydreamService)  {
        return args -> {
                User user1 = new User();
                user1.setUsername("nikola.matovic");
                user1.setEmail("nikola.matovic@ymail.com");
                user1.setPasswordHash("demo123");
                user1.setDisplayName("Nikola Matovic");
                user1 = userService.createUser(user1); // Save and get managed entity

                User user2 = new User();
                user2.setUsername("luca.masella");
                user2.setEmail("luca.masella@ymail.com");
                user2.setPasswordHash("demo123");
                user2.setDisplayName("Luca Masella");
                user2 = userService.createUser(user2); // Save and get managed entity


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
}