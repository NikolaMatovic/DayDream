package ch.fhnw.pizza.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.pizza.data.domain.User;
import ch.fhnw.pizza.data.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }  

    public User addUser(User user) throws Exception {
        return userRepository.save(user);
    }
}
