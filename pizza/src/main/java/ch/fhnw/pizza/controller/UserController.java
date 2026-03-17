package ch.fhnw.pizza.controller;

import ch.fhnw.pizza.business.service.UserService;
import ch.fhnw.pizza.data.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path="/userapi/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path="/users", produces = "application/json")
    public List<User> getUserList() {
        List<User> userList = userService.getAllUsers();
        return userList;
    }

    @GetMapping(path="/users/{id}", produces = "application/json")
    public ResponseEntity getUser(@PathVariable String id) {
        try{
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with given id");
        }
    }
}
