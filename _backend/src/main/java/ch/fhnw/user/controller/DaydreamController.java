package ch.fhnw.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.user.business.service.DaydreamService;
import ch.fhnw.user.data.domain.Daydream;
import ch.fhnw.user.data.domain.User;

@RestController
@RequestMapping("/daydreamapi/v1")
public class DaydreamController {

    @Autowired
    private DaydreamService daydreamService;

    @GetMapping("/daydreams")
    public List<Daydream> getAllDaydreams() {
        return daydreamService.getAllDaydreams();
    }

    @GetMapping("/daydreams/user/{userId}")
    public List<Daydream> getDaydreamsByUser(@PathVariable UUID userId) {
        User user = new User();
        user.setId(userId);
        return daydreamService.getDaydreamsByUser(user);
    }

    @GetMapping("/daydreams/search")
    public List<Daydream> searchDaydreamsByTitle(@RequestParam String title) {
        return daydreamService.searchDaydreamsByTitle(title);
    }

    @PostMapping("/daydreams")
    public Daydream createDaydream(@RequestBody Daydream daydream) {
        return daydreamService.createDaydream(daydream);
    }

    @DeleteMapping("/daydreams/{id}")
    public ResponseEntity<Void> deleteDaydream(@PathVariable UUID id) {
        daydreamService.deleteDaydream(id);
        return ResponseEntity.ok().build();
    }
}
