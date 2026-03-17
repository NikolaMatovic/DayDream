package ch.fhnw.pizza;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.fhnw.pizza.business.service.MenuService;
import ch.fhnw.pizza.business.service.UserService;
import ch.fhnw.pizza.data.domain.Location;
import ch.fhnw.pizza.data.domain.Pizza;
import ch.fhnw.pizza.data.domain.Role;
import ch.fhnw.pizza.data.domain.User;
import ch.fhnw.pizza.data.domain.UserStatus;
import ch.fhnw.pizza.data.repository.LocationRepository;
import ch.fhnw.pizza.data.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Hidden;

@SpringBootApplication
@Hidden
public class PizzaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(
            MenuService menuService,
            UserService userService,
            LocationRepository locationRepository,
            RoleRepository roleRepository) {

        return args -> {
            initPizzas(menuService);
            initUsers(userService, locationRepository, roleRepository);
        };
    }

    private void initPizzas(MenuService menuService) throws Exception {
        Pizza margherita = new Pizza();
        margherita.setPizzaName("Margherita");
        margherita.setPizzaToppings("Tomato sauce, mozzarella, basil");
        menuService.addPizza(margherita);

        Pizza funghi = new Pizza();
        funghi.setPizzaName("Funghi");
        funghi.setPizzaToppings("Tomato sauce, mozzarella, mushrooms");
        menuService.addPizza(funghi);
    }

    private void initUsers(
            UserService userService,
            LocationRepository locationRepository,
            RoleRepository roleRepository) throws Exception {

        if (!roleRepository.findAll().isEmpty() || !locationRepository.findAll().isEmpty()) {
            return;
        }

        Location basel = new Location("FHNW Basel", "Basel");
        Location olten = new Location("FHNW Olten", "Olten");

        locationRepository.saveAll(List.of(basel, olten));

        Role adminRole = new Role("ADMIN", "Administrator with full access");
        adminRole.setPermissions(List.of(
                "CREATE_USER",
                "READ_USER",
                "UPDATE_USER",
                "DELETE_USER",
                "RESET_PASSWORD",
                "VIEW_ACTIVITY_LOGS"
        ));

        Role userRole = new Role("USER", "Standard user");
        userRole.setPermissions(List.of(
                "CHANGE_PASSWORD",
                "VIEW_OWN_PROFILE"
        ));

        roleRepository.saveAll(List.of(adminRole, userRole));

        User admin = new User(
                "nikola.admin",
                "nikola.matovic@fhnw.ch",
                "admin123",
                "Nikola",
                "Matovic",
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                basel,
                adminRole
        );

        User standardUser = new User(
                "luca.user",
                "luca.masella@fhnw.ch",
                "user123",
                "Luca",
                "Masella",
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                olten,
                userRole
        );

        userService.addUser(admin);
        userService.addUser(standardUser);
    }
}