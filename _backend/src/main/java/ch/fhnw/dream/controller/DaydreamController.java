package ch.fhnw.dream.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.dream.business.service.DaydreamService;
import ch.fhnw.dream.data.domain.Daydream;
import ch.fhnw.dream.data.domain.User;

@RestController
@RequestMapping("/v1/dreams")
public class DaydreamController {

    @Autowired
    private DaydreamService daydreamService;

    @GetMapping("/all")
    public List<Daydream> getAllDaydreams() {
        return daydreamService.getAllDaydreams();
    }
}
