package ch.fhnw.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.user.business.service.UserService;
import ch.fhnw.user.data.domain.User;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/userapi/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users", produces = "application/json")
    public List<User> getUserList() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/users/{id}", produces = "application/json")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user found with id " + id);
        }
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User could not be created");
        }
    }

    @PutMapping(path = "/users/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " not found");
        }
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " not found");
        }
    }

    @PatchMapping(path = "/users/{id}/deactivate", produces = "application/json")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        try {
            User user = userService.deactivateUser(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " not found");
        }
    }

    @PatchMapping(path = "/users/{id}/password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String newPassword = body.get("newPassword");

            if (newPassword == null || newPassword.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("newPassword must not be empty");
            }

            User user = userService.changePassword(id, newPassword);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " not found");
        }
    }

    @PatchMapping(path = "/users/{userId}/location/{locationId}", produces = "application/json")
    public ResponseEntity<?> assignLocation(@PathVariable Long userId, @PathVariable Long locationId) {
        try {
            User user = userService.assignLocation(userId, locationId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User or location not found");
        }
    }

    @PatchMapping(path = "/users/{userId}/role/{roleId}", produces = "application/json")
    public ResponseEntity<?> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        try {
            User user = userService.assignRole(userId, roleId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User or role not found");
        }
    }

    @PatchMapping(path = "/users/{id}/last-login", produces = "application/json")
    public ResponseEntity<?> updateLastLogin(@PathVariable Long id) {
        try {
            User user = userService.updateLastLogin(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " not found");
        }
    }
}