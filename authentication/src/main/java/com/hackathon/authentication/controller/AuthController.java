package com.hackathon.authentication.controller;


import com.hackathon.authentication.model.UpdateAccount;
import com.hackathon.authentication.model.User;
import com.hackathon.authentication.model.UserLogin;
import com.hackathon.authentication.security.JwtUtil;
import com.hackathon.authentication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin user, HttpServletResponse response) {
        Optional<User> dbUser = userService.findByUsername(user.getUsername());
        String sentPassword = dbUser.orElse(new User("", "")).getPassword();

        if ((user.getUsername().length() > 3 && user.getPassword().length() > 3) && userService.passwordEncoder().matches(user.getPassword(), sentPassword)) {

            Cookie cookie = new Cookie("token", jwtUtil.genToken(user.getUsername()));
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setMaxAge(36000);
            cookie.setPath("/");

            response.addCookie(cookie);
            return (ResponseEntity<?>) ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>("Not auth", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user, HttpServletResponse response) {
        try {
            if (user.getUsername().length() < 4 || user.getPassword().length() < 4) {
                throw new Exception("Too short");
            }

            Cookie cookie = new Cookie("token", jwtUtil.genToken(user.getUsername()));
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setMaxAge(36000);
            cookie.setPath("/");

            response.addCookie(cookie);
            userService.create(user);
            return (ResponseEntity<?>) ResponseEntity.ok().build();
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return (ResponseEntity<?>) ResponseEntity.ok().build();
    }

    @PutMapping("/update-account")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateAccount newData) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElse(new User("", ""));
        System.out.println(user);

        if (newData.getEmail().trim().isEmpty() && userService.passwordEncoder().matches(newData.getCurrentPassword(), user.getPassword())) {

            user.setPassword(userService.passwordEncoder().encode(newData.getNewPassword()));
            userService.save(user);
            return ResponseEntity.ok().build();
        } else if (!newData.getEmail().trim().isEmpty() && userService.passwordEncoder().matches(newData.getCurrentPassword(), user.getPassword())) {
            user.setEmail(newData.getEmail());
            user.setPassword(userService.passwordEncoder().encode(newData.getNewPassword()));
            userService.save(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
