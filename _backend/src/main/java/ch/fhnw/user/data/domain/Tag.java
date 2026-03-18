package ch.fhnw.user.data.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "daydream_id", nullable = false)
    private Daydream daydream;

    @Column(nullable = false, length = 50)
    private String name;

    public Tag() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Daydream getDaydream() { return daydream; }
    public void setDaydream(Daydream daydream) { this.daydream = daydream; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
