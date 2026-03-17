package ch.fhnw.pizza.data.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonIgnoreProperties("users") // 🔥 verhindert Loop Location → Users → Location
    private Location location;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user") // 🔥 verhindert Loop ActivityLog → User → ActivityLog
    private List<ActivityLog> activityLogs = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String password,
                String firstName, String lastName,
                UserStatus status, LocalDateTime lastLogin,
                Location location, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.lastLogin = lastLogin;
        this.location = location;
        this.role = role;
    }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public UserStatus getStatus() { return status; }

    public LocalDateTime getLastLogin() { return lastLogin; }

    public Location getLocation() { return location; }

    public Role getRole() { return role; }

    public List<ActivityLog> getActivityLogs() { return activityLogs; }

    public void setUsername(String username) { this.username = username; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setStatus(UserStatus status) { this.status = status; }

    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public void setLocation(Location location) { this.location = location; }

    public void setRole(Role role) { this.role = role; }

    public void setActivityLogs(List<ActivityLog> activityLogs) { this.activityLogs = activityLogs; }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}