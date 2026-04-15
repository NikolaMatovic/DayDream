package ch.fhnw.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping({"/login", "/signup", "/feed", "/create", "/profile", "/daydreams/{id}"})
    public String forwardToFrontend() {
        return "forward:/index.html";
    }
}
