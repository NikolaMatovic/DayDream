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
import ch.fhnw.dream.data.repository.DaydreamRepository;
import ch.fhnw.dream.data.repository.UserRepository;

import io.swagger.v3.oas.annotations.Hidden;

@SpringBootApplication
@Hidden
public class DayDreamApplication {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(DayDreamApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserService userService, DaydreamService daydreamService, DaydreamRepository daydreamRepository, UserRepository userRepository)  {
        return args -> {
            daydreamRepository.deleteAll();
            userRepository.deleteAll();

            User luca = createSeedUser(userService, "lucafratello", "luca.masella@students.fhnw.ch", "password", "USER");
            User nikola = createSeedUser(userService, "nidzolesko", "nikola.matovic@students.fhnw.ch", "password", "ADMIN");
            User jasin = createSeedUser(userService, "JasinJson", "jasin.jusufi@students.fhnw.ch", "password", "USER");
            User silvan = createSeedUser(userService, "SilvanoHermano", "silvan.rebmann@students.fhnw.ch", "password", "USER");
            createSeedUser(userService, "admin", "admin@daydream.local", "admin1234", "ADMIN");

            daydreamService.createDaydream(createSeedDaydream(
                luca,
                "Midnight City Sprint",
                "Racing neon trams through a silent city where every station tells a different story.",
                "Electric",
                Visibility.PUBLIC,
                List.of("city", "night", "speed"),
                LocalDateTime.of(2026, 3, 3, 21, 10),
                LocalDateTime.of(2026, 3, 4, 7, 25)
            ));

            daydreamService.createDaydream(createSeedDaydream(
                luca,
                "Grandma's Secret Bakery",
                "A hidden bakery appears only at dawn and serves pastries that replay childhood memories.",
                "Nostalgic",
                Visibility.PRIVATE,
                List.of("bakery", "memories", "dawn"),
                LocalDateTime.of(2026, 3, 6, 6, 45),
                LocalDateTime.of(2026, 3, 6, 8, 5)
            ));

            daydreamService.createDaydream(createSeedDaydream(
                nikola,
                "Cloud Stadium Final",
                "Playing the final match inside a floating stadium while thunder keeps rhythm for the crowd.",
                "Motivated",
                Visibility.PUBLIC,
                List.of("sports", "clouds", "final"),
                LocalDateTime.of(2026, 3, 10, 19, 30),
                LocalDateTime.of(2026, 3, 10, 22, 0)
            ));

            daydreamService.createDaydream(createSeedDaydream(
                nikola,
                "The Last Lighthouse",
                "Guarding an old lighthouse where each beam opens a portal to another coastline.",
                "Mysterious",
                Visibility.PRIVATE,
                List.of("sea", "lighthouse", "portal"),
                LocalDateTime.of(2026, 3, 12, 23, 15),
                LocalDateTime.of(2026, 3, 13, 1, 40)
            ));

            daydreamService.createDaydream(createSeedDaydream(
                jasin,
                "Paper Plane Republic",
                "Building a republic where every law is delivered by paper planes over giant library roofs.",
                "Playful",
                Visibility.PUBLIC,
                List.of("library", "paper-plane", "community"),
                LocalDateTime.of(2026, 3, 15, 14, 5),
                LocalDateTime.of(2026, 3, 15, 16, 55)
            ));

            daydreamService.createDaydream(createSeedDaydream(
                jasin,
                "Silent Arcade",
                "An arcade where games are controlled by gestures and every win paints stars on the walls.",
                "Curious",
                Visibility.PUBLIC,
                List.of("arcade", "stars", "games"),
                LocalDateTime.of(2026, 3, 17, 18, 20),
                LocalDateTime.of(2026, 3, 17, 20, 10)
            ));

            daydreamService.createDaydream(createSeedDaydream(
                silvan,
                "Forest Orchestra",
                "A hidden orchestra in the forest plays with instruments carved from moonlit trees.",
                "Inspired",
                Visibility.PUBLIC,
                List.of("forest", "music", "night"),
                LocalDateTime.of(2026, 3, 20, 5, 40),
                LocalDateTime.of(2026, 3, 20, 7, 0)
            ));

            daydreamService.createDaydream(createSeedDaydream(
                silvan,
                "Snowglobe Workshop",
                "Designing custom snowglobes that capture moments from future adventures.",
                "Hopeful",
                Visibility.PRIVATE,
                List.of("workshop", "snowglobe", "future"),
                LocalDateTime.of(2026, 3, 22, 11, 35),
                LocalDateTime.of(2026, 3, 22, 13, 25)
            ));

        };
    }

    private User createSeedUser(UserService userService, String username, String email, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(PASSWORD_ENCODER.encode(password));
        user.setRole(role);
        return userService.createUser(user);
    }

    private Daydream createSeedDaydream(
            User user,
            String title,
            String description,
            String mood,
            Visibility visibility,
            List<String> tagNames,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        Daydream daydream = new Daydream();
        daydream.setUser(user);
        daydream.setTitle(title);
        daydream.setDescription(description);
        daydream.setMood(mood);
        daydream.setVisibility(visibility);
        daydream.setCreatedAt(createdAt);
        daydream.setUpdatedAt(updatedAt);

        List<Tag> tags = tagNames.stream().map(name -> {
            Tag tag = new Tag();
            tag.setDaydream(daydream);
            tag.setName(name);
            return tag;
        }).toList();

        daydream.setTags(tags);
        return daydream;
    }
}
