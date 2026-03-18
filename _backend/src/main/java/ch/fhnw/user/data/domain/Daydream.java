package ch.fhnw.user.data.domain;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "daydreams")
public class Daydream {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(length = 50)
    private String mood;

    @Column(nullable = false, length = 20)
    private String visibility = "public";

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "daydream", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "daydream", cascade = CascadeType.ALL)
    private List<Tag> tags;

    public Daydream() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public List<Tag> getTags() { return tags; }
    public void setTags(List<Tag> tags) { this.tags = tags; }
}
