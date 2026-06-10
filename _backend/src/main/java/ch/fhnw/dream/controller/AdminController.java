package ch.fhnw.dream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.dream.business.service.DaydreamService;
import ch.fhnw.dream.business.service.UserService;
import ch.fhnw.dream.data.domain.Daydream;
import ch.fhnw.dream.data.domain.User;

@RestController
@RequestMapping("/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private DaydreamService daydreamService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DaydreamController.ErrorResponse("Benutzer nicht gefunden"));
        }
    }

    @GetMapping("/dreams")
    public List<Daydream> getAllDaydreams() {
        return daydreamService.getAllDaydreams();
    }

    @DeleteMapping("/dreams/{id}")
    public ResponseEntity<?> deleteDaydream(@PathVariable Long id) {
        try {
            daydreamService.adminDeleteDaydream(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DaydreamController.ErrorResponse(e.getMessage()));
        }
    }
}
