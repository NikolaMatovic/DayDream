package ch.fhnw.user.data.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "activity_logs")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String action;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("activityLogs") // 🔥 verhindert Loop zurück zum User
    private User user;

    public ActivityLog() {
    }

    public ActivityLog(LocalDateTime timestamp, String action,
                       String description, User user) {
        this.timestamp = timestamp;
        this.action = action;
        this.description = description;
        this.user = user;
    }

    public Long getId() { return id; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public String getAction() { return action; }

    public String getDescription() { return description; }

    public User getUser() { return user; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public void setAction(String action) { this.action = action; }

    public void setDescription(String description) { this.description = description; }

    public void setUser(User user) { this.user = user; }
}