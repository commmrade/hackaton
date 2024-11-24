package com.hackathon.authentication.service;


import com.hackathon.authentication.model.User;
import com.hackathon.authentication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(User user) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void delete(String user) {
        userRepository.deleteByUsername(user);

    }

    public void save(User user) {
        userRepository.save(user);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
