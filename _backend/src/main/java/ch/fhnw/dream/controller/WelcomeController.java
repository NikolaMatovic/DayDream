package ch.fhnw.dream.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@Hidden // Hide this controller from the Swagger UI
@RequestMapping("/api")
public class WelcomeController {

    @GetMapping(value="/welcome")
    public String getWelcomeString() {
        
        return "Hello, welcome to our DayDream Tool!";
    }

    @GetMapping(value="/user-role")
    public String getUserRole(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String role = userDetails.getAuthorities().toArray()[1].toString();
        return role;
    }


}