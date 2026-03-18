package ch.fhnw.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.user.business.service.ActivityLogService;
import ch.fhnw.user.data.domain.ActivityLog;

@RestController
@RequestMapping("/activitylogapi/v1")
public class ActivityLogController {

}