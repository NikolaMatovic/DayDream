package ch.fhnw.user.data.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;

    @OneToMany(mappedBy = "location")
    @JsonIgnore // 🔥 verhindert Loop komplett von dieser Seite
    private List<User> users = new ArrayList<>();

    public Location() {
    }

    public Location(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getCity() { return city; }

    public List<User> getUsers() { return users; }

    public void setName(String name) { this.name = name; }

    public void setCity(String city) { this.city = city; }

    public void setUsers(List<User> users) { this.users = users; }

    public int getUserCount() {
        return users != null ? users.size() : 0;
    }
}