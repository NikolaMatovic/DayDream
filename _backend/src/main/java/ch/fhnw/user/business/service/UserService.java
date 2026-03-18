package ch.fhnw.user.business.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.user.data.domain.Location;
import ch.fhnw.user.data.domain.Role;
import ch.fhnw.user.data.domain.User;
import ch.fhnw.user.data.domain.UserStatus;
import ch.fhnw.user.data.repository.LocationRepository;
import ch.fhnw.user.data.repository.RoleRepository;
import ch.fhnw.user.data.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public User addUser(User user) {
        if (user.getStatus() == null) {
            user.setStatus(UserStatus.ACTIVE);
        }

        if (user.getLastLogin() == null) {
            user.setLastLogin(LocalDateTime.now());
        }

        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = findUserById(id);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setStatus(updatedUser.getStatus());
        existingUser.setLastLogin(updatedUser.getLastLogin());
        existingUser.setLocation(updatedUser.getLocation());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User deactivateUser(Long id) {
        User user = findUserById(id);
        user.setStatus(UserStatus.DEACTIVATED);
        return userRepository.save(user);
    }

    public User changePassword(Long id, String newPassword) {
        User user = findUserById(id);
        user.changePassword(newPassword);
        return userRepository.save(user);
    }

    public User assignLocation(Long userId, Long locationId) {
        User user = findUserById(userId);

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location with id " + locationId + " not found"));

        user.setLocation(location);
        return userRepository.save(user);
    }

    public User assignRole(Long userId, Long roleId) {
        User user = findUserById(userId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role with id " + roleId + " not found"));

        user.setRole(role);
        return userRepository.save(user);
    }

    public User updateLastLogin(Long id) {
        User user = findUserById(id);
        user.setLastLogin(LocalDateTime.now());
        return userRepository.save(user);
    }
}