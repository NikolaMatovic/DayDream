package ch.fhnw.user.business.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.user.data.domain.ActivityLog;
import ch.fhnw.user.data.repository.ActivityLogRepository;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public List<ActivityLog> getAllLogs() {
        return activityLogRepository.findAll();
    }

    public ActivityLog getLogById(Long id) {
        return activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ActivityLog with id " + id + " not found"));
    }

    public List<ActivityLog> getLogsByUserId(Long userId) {
        return activityLogRepository.findByUserId(userId);
    }

    public ActivityLog createLog(ActivityLog log) {
        if (log.getTimestamp() == null) {
            log.setTimestamp(LocalDateTime.now());
        }
        return activityLogRepository.save(log);
    }

    public void deleteLog(Long id) {
        activityLogRepository.deleteById(id);
    }
}