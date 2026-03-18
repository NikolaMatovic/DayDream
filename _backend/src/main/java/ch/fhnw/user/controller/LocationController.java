package ch.fhnw.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.user.business.service.LocationService;
import ch.fhnw.user.data.domain.Location;

@RestController
@RequestMapping("/locationapi/v1")
public class LocationController {

}