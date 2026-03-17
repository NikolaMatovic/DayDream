package ch.fhnw.pizza.data.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    
    private String name;
    private String lastname;

    public User() {}

    public User(String name, String lastname) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.lastname = lastname;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLastname() { return lastname; }
}