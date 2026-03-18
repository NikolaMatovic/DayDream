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

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping("/logs")
    public List<ActivityLog> getAllLogs() {
        return activityLogService.getAllLogs();
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<ActivityLog> getLogById(@PathVariable Long id) {
        return ResponseEntity.ok(activityLogService.getLogById(id));
    }

    @GetMapping("/logs/user/{userId}")
    public List<ActivityLog> getLogsByUserId(@PathVariable Long userId) {
        return activityLogService.getLogsByUserId(userId);
    }

    @PostMapping("/logs")
    public ResponseEntity<ActivityLog> createLog(@RequestBody ActivityLog log) {
        return ResponseEntity.ok(activityLogService.createLog(log));
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        activityLogService.deleteLog(id);
        return ResponseEntity.ok("ActivityLog deleted successfully");
    }
}