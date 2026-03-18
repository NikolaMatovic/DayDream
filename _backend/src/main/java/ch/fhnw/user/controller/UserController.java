package ch.fhnw.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.user.business.service.UserService;
import ch.fhnw.user.data.domain.User;

@RestController
@RequestMapping("/userapi/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUserList() {
        return userService.getAllUsers();
    }

        @GetMapping("/users/{id}")
        public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        return userService.getUserById(id)
            .map(user -> ResponseEntity.ok((Object) user))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No user found with id " + id));
        }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User could not be created");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " not found");
        }
    }
}
